package com.training.restaurant.dto.menus;

import com.fasterxml.jackson.annotation.JsonAlias;

public record UpdateMenuDto(
        @JsonAlias("menu_name")
        String name,
        @JsonAlias("menu_schedule")
        String schedule
) {
}
