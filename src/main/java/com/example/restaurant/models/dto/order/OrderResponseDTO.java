package com.example.restaurant.models.dto.order;

import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDTO {
  private Long orderId;
  private Float total;
  private Date date;
  private CustomerResponseDTO customer;
  private List<DishResponseDTO> dishes;
}
