package com.training.restaurant.dto.dishes;

import com.training.restaurant.models.Dishes;
import com.training.restaurant.models.OrderDish;

public record DishesDto(
        Long id,
        String name,
        String type,
        Double price
) {
    public DishesDto(Dishes dishes) {
        this(
                dishes.getId(),
                dishes.getName(),
                dishes.getType().typeToString(),
                dishes.getPrice());
    }

    public DishesDto(OrderDish orderDish) {
        this(
                orderDish.getDish().getId(),
                orderDish.getDish().getName(),
                orderDish.getDish().getType().typeToString(),
                orderDish.getDish().getPrice());
    }
}
