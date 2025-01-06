package com.restaurant.management.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishOrderResponseDTO {
  private Long dishId;
  private String dishName;
  private Float price;
  private Integer quantity;
}
