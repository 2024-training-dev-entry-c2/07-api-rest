package com.restaurant.restaurant_management.dto;

import com.restaurant.restaurant_management.constants.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
  private Long clientId;
  private OrderStatus status;
}
