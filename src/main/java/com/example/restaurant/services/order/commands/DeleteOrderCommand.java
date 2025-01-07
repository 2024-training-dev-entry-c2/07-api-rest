package com.example.restaurant.services.order.commands;

import com.example.restaurant.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteOrderCommand {

  private final OrderRepository orderRepository;

  public void execute(Long orderId) {
    if (!orderRepository.existsById(orderId)) {
      throw new IllegalArgumentException("No se encontró la orden con ID: " + orderId);
    }
    orderRepository.deleteById(orderId);
  }
}
