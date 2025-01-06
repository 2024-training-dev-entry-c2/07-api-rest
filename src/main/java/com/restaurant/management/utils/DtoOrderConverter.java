package com.restaurant.management.utils;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.Order;
import com.restaurant.management.models.OrderDish;
import com.restaurant.management.models.dto.DishOrderResponseDTO;
import com.restaurant.management.models.dto.OrderRequestDTO;
import com.restaurant.management.models.dto.OrderResponseDTO;

import java.util.List;

public class DtoOrderConverter {
  public static Order toOrder(OrderRequestDTO orderRequestDTO, Client client, List<OrderDish> dishes) {
    Order order = new Order(
      client,
      orderRequestDTO.getDate()
    );
    order.setOrderDishes(dishes);
    return order;
  }

  public static OrderResponseDTO toOrderResponseDTO(Order order) {
    OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
    orderResponseDTO.setId(order.getId());
    orderResponseDTO.setClient(DtoClientConverter.toClientResponseDTO(order.getClient()));
    orderResponseDTO.setDishes(order.getOrderDishes().stream()
      .map(DtoOrderConverter::toDishOrderResponseDTO)
      .toArray(DishOrderResponseDTO[]::new));
    orderResponseDTO.setDate(order.getDate());
    orderResponseDTO.setTotal(order.getTotal());
    return orderResponseDTO;
  }

  private static DishOrderResponseDTO toDishOrderResponseDTO(OrderDish orderDish) {
    DishOrderResponseDTO dishResponseDTO = new DishOrderResponseDTO();
    dishResponseDTO.setDishId(orderDish.getDish().getId());
    dishResponseDTO.setDishName(orderDish.getDish().getName());
    dishResponseDTO.setPrice(orderDish.getDish().getPrice());
    dishResponseDTO.setQuantity(orderDish.getQuantity());
    return dishResponseDTO;
  }
}
