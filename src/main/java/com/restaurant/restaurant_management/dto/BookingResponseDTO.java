package com.restaurant.restaurant_management.dto;

import com.restaurant.restaurant_management.models.Client;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class BookingResponseDTO {
  private Long id;
  private LocalDate date;
  private LocalTime time;
  private Client client;
}
