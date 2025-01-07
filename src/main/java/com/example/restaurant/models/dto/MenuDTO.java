package com.example.restaurant.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
  private Long id;
  private String name;
  private String description;
  private List<DishDTO> dishes; // Lista completa de platos
}