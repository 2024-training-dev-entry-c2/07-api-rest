package com.training.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDishDto(
        @NotBlank
        @JsonAlias("dish_name")
        String name,
        @NotNull
        @JsonAlias("dish_price")
        Double price
) {

}
