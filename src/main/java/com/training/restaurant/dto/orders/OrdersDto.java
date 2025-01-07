package com.training.restaurant.dto.orders;

import com.training.restaurant.dto.dishes.DishWithQuantityDto;
import com.training.restaurant.models.OrderDish;
import com.training.restaurant.models.Orders;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record OrdersDto(
        Long id,
        String customer,
        List<DishWithQuantityDto> dishes,
        Integer quantity,
        Double total,
        ZonedDateTime createdAt
) {
    public OrdersDto(Orders orders) {
        this(
                orders.getId(),
                orders.getCustomer().getName(),
                orders.getOrderDishes().stream().map(DishWithQuantityDto::new).collect(Collectors.toList()),
                orders.getOrderDishes().stream().mapToInt(OrderDish::getQuantity).sum(),
                orders.getTotal(),
                orders.getCreatedAt()
        );
    }
}

