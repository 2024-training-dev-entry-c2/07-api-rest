package com.training.restaurant.dto.orders;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderDishDto(
        @NotBlank
        @JsonAlias("dish_name")
        String name,
        @NotNull
        @JsonAlias("dish_quantity")
        Integer quantity
) {

}
