package com.example.restaurant.dto.order;

import com.example.restaurant.dto.client.ClientResponseDTO;
import com.example.restaurant.dto.dish.DishResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {

    private Long id;
    private ClientResponseDTO client;
    private List<DishResponseDTO> dishes;
    private Float totalPrice;
    private LocalDateTime orderDate;

}
