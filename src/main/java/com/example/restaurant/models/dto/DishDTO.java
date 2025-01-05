package com.example.restaurant.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDTO {
  private Long id;
  private String name;
  private Float price;
  private Long menu; // Relación parcial del menu con id
}