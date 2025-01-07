package com.example.restaurant.services.order.commands;

import com.example.restaurant.models.dto.order.OrderResponseDTO;
import com.example.restaurant.repositories.OrderRepository;
import com.example.restaurant.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderByIdCommand {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  public OrderResponseDTO execute(Long orderId) {
    return orderRepository.findById(orderId)
            .map(orderMapper::toDTO)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró la orden con ID: " + orderId));
  }
}
