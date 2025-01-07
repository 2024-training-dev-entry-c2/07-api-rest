package com.restaurant.restaurant_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailRequestDTO {
  private Integer quantity;
  private Long orderId;
  private Integer dishId;
}
