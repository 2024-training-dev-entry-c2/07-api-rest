package com.restaurant.management.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
  private Long id;
  private ClientResponseDTO client;
  private DishOrderResponseDTO[] dishes;
  private LocalDate date;
  private Float total;
}
