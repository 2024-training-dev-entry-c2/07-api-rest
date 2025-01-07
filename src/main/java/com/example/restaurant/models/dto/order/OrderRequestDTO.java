package com.example.restaurant.models.dto.order;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderRequestDTO {
  private Date date;
  private Long customerId;
  private List<Long> dishIds; // IDs de platos
}
