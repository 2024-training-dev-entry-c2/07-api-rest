package com.restaurant.restaurant_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteOrderRequestDTO {
  private OrderRequestDTO orderRequestDTO;
  private List<DetailRequestDTO> details;
}
