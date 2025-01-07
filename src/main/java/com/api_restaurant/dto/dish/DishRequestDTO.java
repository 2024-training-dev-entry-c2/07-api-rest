package com.api_restaurant.dto.dish;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishRequestDTO {
    private String name;
    private String description;
    private Double price;
    private Long menuId;
}
