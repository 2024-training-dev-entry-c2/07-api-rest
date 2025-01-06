package com.training.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateMenuDto(

        @NotBlank
        @JsonAlias("menu_name")
        String name,
        @NotBlank
        @JsonAlias("menu_schedule")
        String schedule,
        @Valid
        @JsonAlias("menu_dishes")
        List<CreateDishDto> dishes
) {
}
