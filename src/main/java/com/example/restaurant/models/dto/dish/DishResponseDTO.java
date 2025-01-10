package com.example.restaurant.models.dto.dish;

import lombok.Data;

@Data
public class DishResponseDTO {
    private Long dishId;
    private String name;
    private Float price;
//    private Long menuId; // Incluido para información de referencia

    public DishResponseDTO(Long dishId, String name, Float price) {
        this.dishId = dishId;
        this.name = name;
        this.price = price;
    }

    public DishResponseDTO() {
    }
}
