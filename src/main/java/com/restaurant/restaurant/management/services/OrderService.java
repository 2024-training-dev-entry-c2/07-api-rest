package com.restaurant.restaurant.management.services;

import com.restaurant.restaurant.management.dto.OrderItemResponseDto;
import com.restaurant.restaurant.management.dto.OrderResponseDto;
import com.restaurant.restaurant.management.models.*;
import com.restaurant.restaurant.management.repositories.ClientRepository;
import com.restaurant.restaurant.management.repositories.DishRepository;
import com.restaurant.restaurant.management.repositories.OrderItemRepository;
import com.restaurant.restaurant.management.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final ClientRepository clientRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, DishRepository dishRepository, ClientRepository clientRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
        this.clientRepository = clientRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Optional<Client> getClientByName(String clientName) {
        return clientRepository.findAll().stream()
                .filter(client -> client.getClientName().equalsIgnoreCase(clientName))
                .findFirst();
    }

    public OrderRestaurant addOrder(OrderRestaurant orderRestaurant, Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isPresent()) {
            orderRestaurant.setClient(client.get());
            return orderRepository.save(orderRestaurant);
        } else {
            throw new RuntimeException("El cliente con ID: " + clientId + " no fue encontrado");
        }
    }

    public List<OrderRestaurant> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<OrderRestaurant> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    public OrderRestaurant updateOrder(Long id, OrderRestaurant orderUpdated) {
        return orderRepository.findById(id).map(existingOrder -> {
            existingOrder.setTotalAmount(orderUpdated.getTotalAmount());
            existingOrder.getOrderItems().clear();
            if (orderUpdated.getOrderItems() != null) {
                orderUpdated.getOrderItems().forEach(item -> {
                    item.setOrder(existingOrder);
                    existingOrder.getOrderItems().add(item);
                });
            }
            return orderRepository.save(existingOrder);
        }).orElseThrow(() -> new RuntimeException("El pedido con id: " + id + " no pudo ser actualizado"));
    }


    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderItem addItemToOrder(Long idOrder, OrderItem orderItem) {
        Optional<OrderRestaurant> orderOpt = orderRepository.findById(idOrder);
        if (orderOpt.isPresent()) {
            OrderRestaurant order = orderOpt.get();

            Optional<Dish> dishOpt = dishRepository.findById(orderItem.getIdDish());
            if (dishOpt.isPresent()) {
                orderItem.setDish(dishOpt.get());
            } else {
                throw new RuntimeException("Dish not found with id: " + orderItem.getIdDish());
            }
            orderItem.setOrder(order);
            OrderItem savedItem = orderItemRepository.save(orderItem);
            order.getOrderItems().add(savedItem);
            orderRepository.save(order);

            return savedItem;
        }
        return null;
    }

    public Optional<OrderItem> getOrderItemById(Long idOrderItem) {
        return orderItemRepository.findById(idOrderItem);
    }

    public OrderItem updateOrderItem(Long idOrderItem, OrderItem updatedOrderItem) {
        return orderItemRepository.findById(idOrderItem).map(existingOrderItem -> {
            Optional<Dish> dishOpt = dishRepository.findById(updatedOrderItem.getIdDish());
            if (dishOpt.isPresent()) {
                existingOrderItem.setDish(dishOpt.get());
            } else {
                throw new RuntimeException("Dish not found with id: " + updatedOrderItem.getIdDish());
            }
            existingOrderItem.setQuantity(updatedOrderItem.getQuantity());
            return orderItemRepository.save(existingOrderItem);
        }).orElseThrow(() -> new RuntimeException("OrderItem with id: " + idOrderItem + " not found"));
    }

    public void deleteOrderItem(Long idOrderItem) {
        if (orderItemRepository.existsById(idOrderItem)) {
            orderItemRepository.deleteById(idOrderItem);
        } else {
            throw new RuntimeException("OrderItem with id: " + idOrderItem + " not found");
        }
    }



}
