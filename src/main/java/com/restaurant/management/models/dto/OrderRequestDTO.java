package com.restaurant.management.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderRequestDTO {
  private ClientRequestDTO client;
  private LocalDate date;
}
