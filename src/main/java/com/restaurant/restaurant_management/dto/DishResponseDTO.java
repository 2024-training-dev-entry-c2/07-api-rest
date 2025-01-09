package com.restaurant.restaurant_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishResponseDTO {
  private Integer id;
  private String dishName;
  private String description;
  private Integer basePrice;
  private Boolean isPopular;
  private Boolean active;
}
