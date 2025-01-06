package com.restaurant.management.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResponseDTO {
  private Long id;
  private String name;
  private List<DishResponseDTO> dishes;
}
