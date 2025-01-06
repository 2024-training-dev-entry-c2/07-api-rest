package com.restaurant.management.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MenuResponseDTO {
  private Long id;
  private String name;
  private Set<DishResponseDTO> dishes;
}
