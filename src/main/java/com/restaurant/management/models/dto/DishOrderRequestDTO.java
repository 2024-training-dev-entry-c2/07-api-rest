package com.restaurant.management.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishOrderRequestDTO {
  private Long dishId;
  private Integer quantity;
}
