package com.restaurant.restaurant_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequestDTO {
  private String menuName;
  private String description;
  private Boolean active;
}
