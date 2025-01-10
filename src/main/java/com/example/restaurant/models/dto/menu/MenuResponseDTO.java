package com.example.restaurant.models.dto.menu;

import com.example.restaurant.models.dto.dish.DishResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class MenuResponseDTO {
  private Long menuId;
  private String name;
  private String description;
  private List<DishResponseDTO> dishes;

  public MenuResponseDTO(Long menuId, String name, String description) {
    this.menuId = menuId;
    this.name = name;
    this.description = description;
  }

  public MenuResponseDTO() {
  }
}
