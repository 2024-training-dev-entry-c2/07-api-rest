package com.example.restaurant.services.order.commands;

import com.example.restaurant.models.Order;
import com.example.restaurant.models.dto.order.OrderRequestDTO;
import com.example.restaurant.models.dto.order.OrderResponseDTO;
import com.example.restaurant.repositories.CustomerRepository;
import com.example.restaurant.repositories.DishRepository;
import com.example.restaurant.repositories.OrderRepository;
import com.example.restaurant.mappers.OrderMapper;
import com.example.restaurant.services.handlers.RegularCustomerDiscountHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateOrderCommand {

  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;
  private final DishRepository dishRepository;
  private final RegularCustomerDiscountHandler regularCustomerDiscountHandler;
  private final OrderMapper orderMapper;

  public OrderResponseDTO execute(Long orderId, OrderRequestDTO orderDTO) {
    Order existingOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("No se encontró la orden con ID: " + orderId));

    Order updatedOrder = orderMapper.toEntity(orderDTO);
    Order savedOrder = orderRepository.save(updatedOrder);
    return orderMapper.toDTO(savedOrder);
  }
}
