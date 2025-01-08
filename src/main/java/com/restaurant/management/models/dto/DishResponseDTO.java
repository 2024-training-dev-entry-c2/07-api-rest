package com.restaurant.management.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishResponseDTO {
  private Long id;
  private String name;
  private String description;
  private Float price;
  private String state;
  private Long menuId;
}
