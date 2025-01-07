package com.restaurant.restaurant_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuResponseDTO {
  private Integer id;
  private String menuName;
  private String description;
  private Boolean active;
}
