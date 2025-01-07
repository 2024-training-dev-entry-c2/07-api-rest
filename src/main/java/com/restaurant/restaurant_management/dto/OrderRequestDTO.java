package com.restaurant.restaurant_management.dto;

import com.restaurant.restaurant_management.constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
  private Long clientId;
  private OrderStatus status;
}
