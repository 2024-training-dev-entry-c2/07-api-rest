package com.example.restaurant.services.order.commands;

import com.example.restaurant.models.Order;
import com.example.restaurant.models.dto.OrderDTO;
import com.example.restaurant.repositories.OrderRepository;
import com.example.restaurant.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderCommand {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  public OrderDTO execute(OrderDTO orderDTO) {
    Order order = orderMapper.toEntity(orderDTO);
    order = orderRepository.save(order);
    return orderMapper.toDTO(order);
  }
}
