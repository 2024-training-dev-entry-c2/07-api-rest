package com.example.restaurant.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
  private Long id;
  private Float total;
  private Date date;
  private CustomerDTO customer; // Relación completa al cliente
  private List<DishDTO> dishes; // Lista completa de platos asociados
}