package com.restaurant.restaurant_management.dto;

import com.restaurant.restaurant_management.models.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponseDTO {
  private Long id;
  private Integer quantity;
  private Double subtotal;
  private Long orderId;
  private Dish dish;
}
