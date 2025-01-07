package com.restaurant.restaurant_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequestDTO {
  private String name;
  private String lastName;
  private String email;
  private String phone;
  private String address;
  private Boolean isFrequent;
}
