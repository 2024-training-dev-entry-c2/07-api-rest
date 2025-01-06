package com.example.restaurant.services.order.commands;

import com.example.restaurant.models.Order;
import com.example.restaurant.models.dto.OrderDTO;
import com.example.restaurant.repositories.OrderRepository;
import com.example.restaurant.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateOrderCommand {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  public OrderDTO execute(Long orderId, OrderDTO orderDTO) {
    Order existingOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró la orden con ID: " + orderId));

    // Actualizar los atributos relevantes de la orden
    existingOrder.setTotal(orderDTO.getTotal());
    existingOrder.setDate(orderDTO.getDate());
    existingOrder.setDishes(orderMapper.toEntity(orderDTO).getDishes());

    existingOrder = orderRepository.save(existingOrder);
    return orderMapper.toDTO(existingOrder);
  }
}
