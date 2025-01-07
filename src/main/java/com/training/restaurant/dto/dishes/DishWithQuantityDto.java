package com.training.restaurant.dto.dishes;

import com.training.restaurant.models.OrderDish;

public record DishWithQuantityDto(
        Long id,
        String name,
        String type,
        Double price,
        Integer quantity
) {
    public DishWithQuantityDto(OrderDish orderDish) {
        this(
                orderDish.getDish().getId(),
                orderDish.getDish().getName(),
                orderDish.getDish().getType().typeToString(),
                orderDish.getDish().getPrice(),
                orderDish.getQuantity()
        );
    }
}

