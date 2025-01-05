package com.example.restaurant.mapper;

import com.example.restaurant.models.dto.OrderDTO;
import com.example.restaurant.models.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

  public OrderDTO toDTO(Order order) {
    return new OrderDTO(
            order.getId(),
            order.getTotal(),
            order.getDate(),
            new CustomerMapper().toDTO(order.getCustomer()),
            order.getDishes() != null ? order.getDishes()
                    .stream()
                    .map(dish -> new DishMapper().toDTO(dish))
                    .collect(Collectors.toList()) : null
    );
  }

  public Order toEntity(OrderDTO orderDTO) {
    return new Order(
            orderDTO.getId(),
            orderDTO.getTotal(),
            orderDTO.getDate(),
            null, // Evitamos mapear cliente al crear un pedido
            null // Evitamos mapear platos al crear un pedido
    );
  }
}
