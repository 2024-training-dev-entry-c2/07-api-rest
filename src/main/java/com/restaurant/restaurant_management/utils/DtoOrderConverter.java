package com.restaurant.restaurant_management.utils;

import com.restaurant.restaurant_management.dto.OrderRequestDTO;
import com.restaurant.restaurant_management.dto.OrderResponseDTO;
import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.models.ClientOrder;

public class DtoOrderConverter {

  public static ClientOrder convertToOrder(OrderRequestDTO orderRequestDTO, Client client){
    ClientOrder order = new ClientOrder();
    order.setStatus(orderRequestDTO.getStatus());
    order.setClient(client);
    return order;
  }

  public static OrderResponseDTO convertToResponseDTO(ClientOrder order){
    OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
    orderResponseDTO.setId(order.getId());
    orderResponseDTO.setOrderDateTime(order.getOrderDateTime());
    orderResponseDTO.setStatus(order.getStatus());
    orderResponseDTO.setTotal(order.getTotal());
    orderResponseDTO.setClient(order.getClient());
    orderResponseDTO.setOrderDetails(order.getOrderDetails());
    return orderResponseDTO;
  }

}
