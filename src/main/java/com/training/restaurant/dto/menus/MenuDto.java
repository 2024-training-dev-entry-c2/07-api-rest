package com.training.restaurant.dto.menus;

import com.training.restaurant.dto.dishes.DishesDto;
import com.training.restaurant.models.Menu;

import java.util.List;

public record MenuDto(
        Long id,
        String name,
        String schedule,
        List<DishesDto> dishes

) {
    public MenuDto(Menu menu) {
        this(
                menu.getId(),
                menu.getName(),
                menu.getSchedule(),
                menu.getDishes().stream().map(DishesDto::new).toList());
    }
}
