package com.restaurant.restaurant.dtos;

import com.restaurant.restaurant.enums.DishType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDTO {
  private Long id;
  private String name;
  private Double price;
  private DishType type;
}
