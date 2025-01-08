package com.restaurant.management.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishRequestDTO {
  private String name;
  private String description;
  private Float price;
  private Long menuId;
}
