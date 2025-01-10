package com.example.restaurant.models.dto.order;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderRequestDTO {
  private Date date;
  private Long customerId;
  private List<Long> dishIds; // IDs de platos

  public OrderRequestDTO(Date date, Long customerId, List<Long> dishIds) {
    this.date = date;
    this.customerId = customerId;
    this.dishIds = dishIds;
  }

  public OrderRequestDTO() {
  }
}
