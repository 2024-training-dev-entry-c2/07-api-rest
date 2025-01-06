package com.restaurant.management.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
  private Long clientId;
  private List<DishOrderRequestDTO> dishes;
  private LocalDate date;
}
