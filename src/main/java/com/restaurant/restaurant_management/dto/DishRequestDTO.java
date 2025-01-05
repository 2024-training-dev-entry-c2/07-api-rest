package com.restaurant.restaurant_management.dto;

import lombok.Getter;

@Getter
public class DishRequestDTO {
  private String dishName;
  private String description;
  private Integer basePrice;
  private Boolean isPopular;
  private Integer menuId;
}
