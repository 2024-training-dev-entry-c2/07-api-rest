package com.example.restaurant.models.dto.order;

import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDTO {
  private Long orderId;
  private Float totalOrderPrice;
  private Date date;
  private CustomerResponseDTO customer;
  private List<DishResponseDTO> dishes;

  public OrderResponseDTO(Long orderId, Date date, CustomerResponseDTO customer, List<DishResponseDTO> dishes) {
    this.orderId = orderId;
    this.date = date;
    this.customer = customer;
    this.dishes = dishes;
  }

  public OrderResponseDTO() {
  }
}
