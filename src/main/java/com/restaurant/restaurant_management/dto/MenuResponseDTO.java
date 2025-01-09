package com.restaurant.restaurant_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDTO {
  private Integer id;
  private String menuName;
  private String description;
  private Boolean active;
}
