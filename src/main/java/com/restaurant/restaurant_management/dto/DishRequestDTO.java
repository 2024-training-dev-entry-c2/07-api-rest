package com.restaurant.restaurant_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDTO {
  private String dishName;
  private String description;
  private Integer basePrice;
  private Boolean isPopular;
  private Integer menuId;
  private Boolean active;
}
