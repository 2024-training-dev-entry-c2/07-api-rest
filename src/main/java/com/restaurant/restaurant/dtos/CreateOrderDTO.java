package com.restaurant.restaurant.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDTO {
  private Long clientId;
  private List<Long> dishIds;
}
