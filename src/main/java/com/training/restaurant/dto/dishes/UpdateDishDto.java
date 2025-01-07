package com.training.restaurant.dto.dishes;

import com.fasterxml.jackson.annotation.JsonAlias;

public record UpdateDishDto(
        @JsonAlias("dish_name")
        String name,
        @JsonAlias("dish_type")
        String type,
        @JsonAlias("dish_price")
        Double price
) {
}
