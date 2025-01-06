package com.restaurant.management.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponseDTO {
  private Long id;
  private String name;
  private String email;
  private Boolean frequent;
}
