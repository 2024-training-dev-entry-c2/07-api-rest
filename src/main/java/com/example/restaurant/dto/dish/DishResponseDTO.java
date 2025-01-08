package com.example.restaurant.dto.dish;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Float price;
    private String dishType;
    private String menuName;

}
