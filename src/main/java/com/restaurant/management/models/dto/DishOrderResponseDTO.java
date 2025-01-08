package com.restaurant.management.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishOrderResponseDTO {
  private Long dishId;
  private String dishName;
  private Float price;
  private Integer quantity;
}
