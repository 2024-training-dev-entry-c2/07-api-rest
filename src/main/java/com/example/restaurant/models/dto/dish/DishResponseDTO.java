package com.example.restaurant.models.dto.dish;

import lombok.Data;

@Data
public class DishResponseDTO {
    private Long id;
    private String name;
    private Float price;
//    private Long menuId; // Incluido para información de referencia

    public DishResponseDTO(Long id, String name, Float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public DishResponseDTO() {
    }
}
