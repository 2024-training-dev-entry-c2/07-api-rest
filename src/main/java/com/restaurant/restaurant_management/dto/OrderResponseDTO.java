package com.restaurant.restaurant_management.dto;

import com.restaurant.restaurant_management.constants.OrderStatus;
import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.models.OrderDetail;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {
  private Long id;
  private LocalDateTime orderDateTime;
  private OrderStatus status;
  private Double total;
  private Client client;
  private List<OrderDetail> orderDetails = new ArrayList<>();
}
