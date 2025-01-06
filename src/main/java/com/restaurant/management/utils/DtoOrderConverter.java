package com.restaurant.management.utils;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Order;
import com.restaurant.management.models.dto.DishResponseDTO;
import com.restaurant.management.models.dto.OrderRequestDTO;
import com.restaurant.management.models.dto.OrderResponseDTO;

import java.util.List;

public class DtoOrderConverter {
  public static Order toOrder(OrderRequestDTO orderRequestDTO, Client client, List<Dish> dishes) {
    Order order = new Order(
      client,
      orderRequestDTO.getDate()
    );
    order.setDishes(dishes);
    return order;
  }

  public static OrderResponseDTO toOrderResponseDTO(Order order) {
    OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
    orderResponseDTO.setId(order.getId());
    orderResponseDTO.setClient(DtoClientConverter.toClientResponseDTO(order.getClient()));
    orderResponseDTO.setDishes(order.getDishes().stream()
      .map(DtoDishConverter::toDishResponseDTO)
      .toArray(DishResponseDTO[]::new));
    orderResponseDTO.setDate(order.getDate());
    orderResponseDTO.setTotal(order.getTotal());
    return orderResponseDTO;
  }
}
