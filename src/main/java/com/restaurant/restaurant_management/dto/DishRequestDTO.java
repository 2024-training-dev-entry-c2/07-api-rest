package com.restaurant.restaurant_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishRequestDTO {
  private String dishName;
  private String description;
  private Integer basePrice;
  private Boolean isPopular;
  private Integer menuId;
  private Boolean active;
}
