package com.restaurant.restaurant_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompleteOrderRequestDTO {
  private OrderRequestDTO orderRequestDTO;
  private List<DetailRequestDTO> details;
}
