package com.restaurant.management.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDTO {
  private Long id;
  private String name;
  private List<DishResponseDTO> dishes;
}
