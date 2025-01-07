package com.restaurante.restaurante.services.impl;

import com.restaurante.restaurante.chain.OrderProcessChain;
import com.restaurante.restaurante.decorator.BaseOrderPrice;
import com.restaurante.restaurante.decorator.FrequentClientDiscount;
import com.restaurante.restaurante.decorator.OrderPrice;
import com.restaurante.restaurante.decorator.PopularDishPriceIncrease;
import com.restaurante.restaurante.dto.OrderDTO;
import com.restaurante.restaurante.mapper.OrderMapper;
import com.restaurante.restaurante.models.Orders;
import com.restaurante.restaurante.models.Client;
import com.restaurante.restaurante.models.Dish;
//import com.restaurante.restaurante.observer.OrderSubject;
import com.restaurante.restaurante.observer.DishObserver;
import com.restaurante.restaurante.observer.OrderSubject;
import com.restaurante.restaurante.repositories.OrderRepository;
import com.restaurante.restaurante.repositories.ClientRepository;
import com.restaurante.restaurante.repositories.DishRepository;
import com.restaurante.restaurante.services.IOrderService;
import com.restaurante.restaurante.utils.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ClientRepository clientRepository;
    private final DishRepository dishRepository;
    private final OrderSubject orderSubject;
    private final OrderProcessChain orderProcessChain;
    private final DishObserver dishObserver;



    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderMapper orderMapper,
                            ClientRepository clientRepository,
                            DishRepository dishRepository,
                            OrderSubject orderSubject,
                            OrderProcessChain orderProcessChain,
                            DishObserver dishObserver) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.clientRepository = clientRepository;
        this.dishRepository = dishRepository;
        this.orderSubject = orderSubject;
        this.orderProcessChain = orderProcessChain;
        this.dishObserver = dishObserver;
    }







    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        Orders order = orderMapper.toEntity(orderDTO);
        order.setDateOrder(LocalDateTime.now().toString());

        Client client = clientRepository.findById(orderDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        order.setClient(client);

        if (orderDTO.getDishes() != null && !orderDTO.getDishes().isEmpty()) {
            // Procesar y actualizar platos
            List<Dish> dishes = orderDTO.getDishes().stream()
                    .map(dishDTO -> dishRepository.findByName(dishDTO.getName())
                            .orElseThrow(() -> new RuntimeException("Plato no encontrado: " + dishDTO.getName())))
                    .collect(Collectors.toList());

            order.setDishes(dishes);

            // Calcular precio usando el patrón Decorator
            OrderPrice basePrice = new BaseOrderPrice(order);
            Double calculatedPrice = basePrice.calculatePrice();


            if (UserType.FREQUENT.toString().equals(client.getUserType())) {
                basePrice = new FrequentClientDiscount(basePrice);
                calculatedPrice = basePrice.calculatePrice();
            }

            order.setTotalPrice(calculatedPrice);
        } else {
            order.setTotalPrice(0.0);
        }

        // Guardar la orden y procesar con la cadena
        Orders savedOrder = orderRepository.save(order);
        orderProcessChain.processOrder(savedOrder);
        savedOrder = orderRepository.save(savedOrder);

        // Notificar a los observadores
        orderSubject.notifyObservers(savedOrder.getId());

        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public Optional<OrderDTO> getOrder(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO);
    }

    @Override
    public List<OrderDTO> getOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        return orderRepository.findById(id)
                .map(existingOrder -> updateExistingOrder(existingOrder, orderDTO))
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
    }

    private OrderDTO updateExistingOrder(Orders existingOrder, OrderDTO orderDTO) {
        updateBasicFields(existingOrder, orderDTO);
        updateClientIfPresent(existingOrder, orderDTO);
        updateDishesAndPrice(existingOrder, orderDTO);
        return saveAndMapOrder(existingOrder);
    }

    private void updateBasicFields(Orders order, OrderDTO orderDTO) {
        order.setDateOrder(orderDTO.getOrderDate());
    }

    private void updateClientIfPresent(Orders order, OrderDTO orderDTO) {
        Optional.ofNullable(orderDTO.getClientId())
                .ifPresent(clientId -> {
                    Client client = clientRepository.findById(clientId)
                            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
                    order.setClient(client);
                });
    }

    private void updateDishesAndPrice(Orders order, OrderDTO orderDTO) {
        if (hasDishes(orderDTO)) {
            order.setDishes(getDishesFromDTO(orderDTO));
            updateOrderPrice(order);
        }
    }

    private boolean hasDishes(OrderDTO orderDTO) {
        return orderDTO.getDishes() != null && !orderDTO.getDishes().isEmpty();
    }

    private List<Dish> getDishesFromDTO(OrderDTO orderDTO) {
        return orderDTO.getDishes().stream()
                .map(dishDTO -> dishRepository.findByName(dishDTO.getName())
                        .orElseThrow(() -> new RuntimeException("Plato no encontrado: " + dishDTO.getName())))
                .collect(Collectors.toList());
    }

    private void updateOrderPrice(Orders order) {
        OrderPrice basePrice = new BaseOrderPrice(order);
        if (isFrequentClient(order)) {
            basePrice = new FrequentClientDiscount(basePrice);
        }
        order.setTotalPrice(basePrice.calculatePrice());
    }

    private boolean isFrequentClient(Orders order) {
        return UserType.FREQUENT.toString().equals(order.getClient().getUserType());
    }

    private OrderDTO saveAndMapOrder(Orders order) {
        return orderMapper.toDTO(orderRepository.save(order));
    }
}