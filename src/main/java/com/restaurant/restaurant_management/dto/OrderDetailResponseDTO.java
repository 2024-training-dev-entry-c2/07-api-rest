package com.restaurant.restaurant_management.dto;

import com.restaurant.restaurant_management.models.Dish;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailResponseDTO {
  private Long id;
  private Integer quantity;
  private Double subtotal;
  private Long orderId;
  private Dish dish;
}
