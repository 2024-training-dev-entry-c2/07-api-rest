package com.api_restaurant.dto.order;

import com.api_restaurant.dto.dish.DishResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {
    private Long id;
    private Long clientId;
    private List<DishResponseDTO> dishes;
    private Double total;
}