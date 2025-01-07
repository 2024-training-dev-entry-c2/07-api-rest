package com.example.restaurant.models.dto.dish;

import lombok.Data;

@Data
public class DishRequestDTO {
    private String name;
    private Float price;
//    private Long menuId; // Para asociar el plato al menú
}
