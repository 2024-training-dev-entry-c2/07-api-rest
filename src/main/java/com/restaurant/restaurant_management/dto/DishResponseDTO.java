package com.restaurant.restaurant_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponseDTO {
  private Integer id;
  private String dishName;
  private String description;
  private Integer basePrice;
  private Float price;
  private Boolean isPopular;
  private Boolean active;
}
