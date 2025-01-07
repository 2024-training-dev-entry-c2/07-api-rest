package com.restaurant.restaurant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
  private Long id;
  private Long clientId;
  private List<Long> dishIds;
  private LocalDateTime date;
}
