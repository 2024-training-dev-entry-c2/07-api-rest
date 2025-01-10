package com.restaurant.restaurant.dtos;

import com.restaurant.restaurant.enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
  private Long id;
  private String name;
  private String lastName;
  private String email;
  private String phone;
  private ClientType type;
}
