package com.restaurant.management.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequestDTO {
  private String name;
  private String email;
  private Boolean frequent;
}
