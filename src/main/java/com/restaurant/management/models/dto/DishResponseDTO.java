package com.restaurant.management.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponseDTO {
  private Long id;
  private String name;
  private String description;
  private Float price;
  private String state;
  private Long menuId;
}
