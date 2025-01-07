package com.restaurante.restaurante.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String orderDate;
    private Double totalPrice = 0.0;
    private Long clientId;
    private List<DishDTO> dishes;

}