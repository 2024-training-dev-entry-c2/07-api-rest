package com.restaurant.restaurant_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {
  private Long id;
  private String name;
  private String lastName;
  private String email;
  private String phone;
  private String address;
  private Boolean isFrequent;
}
