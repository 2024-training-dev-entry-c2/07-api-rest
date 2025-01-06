package com.example.restaurant.services.order.commands;

import com.example.restaurant.models.dto.OrderDTO;
import com.example.restaurant.repositories.OrderRepository;
import com.example.restaurant.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListOrdersCommand {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  public List<OrderDTO> execute() {
    return orderRepository.findAll()
            .stream()
            .map(orderMapper::toDTO)
            .toList();
  }
}
