package com.example.restaurant.models.dto.customer;

import lombok.Data;

@Data
public class CustomerRequestDTO {
  private String name;
  private String lastName;
  private String email;
  private String phone;
}
