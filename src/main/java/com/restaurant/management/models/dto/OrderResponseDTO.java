package com.restaurant.management.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderResponseDTO {
  private Long id;
  private ClientResponseDTO client;
  private DishOrderResponseDTO[] dishes;
  private LocalDate date;
  private Float total;
}
