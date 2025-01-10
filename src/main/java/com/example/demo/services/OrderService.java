package com.example.demo.services;

import com.example.demo.DTO.converterDTO.OrderConverter;
import com.example.demo.DTO.order.OrderRequestDTO;
import com.example.demo.DTO.order.OrderResponseDTO;
import com.example.demo.models.Client;
import com.example.demo.models.Dishfood;
import com.example.demo.models.Order;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.DishfoodRepository;
import com.example.demo.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderPricingService pricingService;
    private final ClientValidationService clientValidationService;
    private final DishfoodValidationService dishfoodValidationService;
    private final ClientRepository clientRepository;
    private final DishfoodRepository dishfoodRepository;

    public OrderService(OrderRepository repository, OrderPricingService pricingService, ClientValidationService clientValidationService, DishfoodValidationService dishfoodValidationService, ClientRepository clientRepository, DishfoodRepository dishfoodRepository) {
        this.repository = repository;
        this.pricingService = pricingService;
        this.clientValidationService = clientValidationService;
        this.dishfoodValidationService = dishfoodValidationService;
        this.clientRepository = clientRepository;
        this.dishfoodRepository = dishfoodRepository;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO orderDTO) {

        Client client = clientRepository.findById(orderDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        List<Dishfood> dishfoods =  dishfoodRepository.findAllById(orderDTO.getDishfoodIds());
        double total = pricingService.calculateTotalPrice(dishfoods);
        Order order = OrderConverter.toEntity(orderDTO, client, dishfoods, total);
        pricingService.applyPricingRules(order);
        Order savedOrder = repository.save(order);
        clientValidationService.checkClient(order);
        dishfoodValidationService.checkDishFood(order);

        return OrderConverter.toResponseDTO(savedOrder);
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderConverter.toResponseDTO(order);
    }

    public List<OrderResponseDTO> getAllOrders() {
        return repository.findAll().stream()
                .map(OrderConverter::toResponseDTO)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        Client client = clientRepository.findById(orderRequestDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        order.setLocalDate(orderRequestDTO.getLocalDate());
        order.setClient(client);

        Order updatedOrder = repository.save(order);

        return OrderConverter.toResponseDTO(updatedOrder);
    }

    public void removeOrder(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        repository.deleteById(id);
    }


}
