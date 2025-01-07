package com.training.restaurant.dto.orders;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderDto(
        @NotEmpty(message = "La lista de platos no puede estar vacia")
        @JsonAlias("order_dishes")
        List<OrderDishDto> dishes
) {
}
