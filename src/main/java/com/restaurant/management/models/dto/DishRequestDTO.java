package com.restaurant.management.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishRequestDTO {
  private String name;
  private String description;
  private Float price;
  private Long menuId;
}
