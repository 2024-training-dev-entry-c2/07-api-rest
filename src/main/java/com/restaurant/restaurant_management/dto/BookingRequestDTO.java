package com.restaurant.restaurant_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class BookingRequestDTO {
  private LocalDate date;
  private LocalTime time;
  private Long clientId;
}
