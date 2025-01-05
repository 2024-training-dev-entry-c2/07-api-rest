package com.example.restaurant.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
  private Long id;
  private String name;
  private String lastName;
  private String email;
  private String phone;
  private List<OrderDTO> orders; // Lista completa de pedidos asociados
}