package com.example.restaurant.models.dto.dish;

import lombok.Data;

@Data
public class DishResponseDTO {
    private Long customerId;
    private String name;
    private Float price;
//    private Long menuId; // Incluido para información de referencia

    public DishResponseDTO(Long customerId, String name, Float price) {
        this.customerId = customerId;
        this.name = name;
        this.price = price;
    }

    public DishResponseDTO() {
    }
}
