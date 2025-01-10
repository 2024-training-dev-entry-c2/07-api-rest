package com.example.restaurant.models.dto.menu;

import com.example.restaurant.models.dto.dish.DishResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class MenuResponseDTO {
  private Long id;
  private String name;
  private String description;
  private List<DishResponseDTO> dishes;

  public MenuResponseDTO(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public MenuResponseDTO() {
  }
}
