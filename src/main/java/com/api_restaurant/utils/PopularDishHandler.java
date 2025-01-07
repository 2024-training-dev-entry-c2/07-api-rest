package com.api_restaurant.utils;

import com.api_restaurant.models.Dish;
import com.api_restaurant.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PopularDishHandler implements DishHandler {
    private DishHandler nextHandler;
    private final OrderRepository orderRepository;

    @Autowired
    public PopularDishHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void setNext(DishHandler handler) {
        this.nextHandler = handler;
    }

    public void handle(Dish dish) {
        long orderCount = orderRepository.countByDishId(dish.getId());
        if (orderCount > 100) {
            dish.setSpecialDish(true);
        }
        if (nextHandler != null) {
            nextHandler.handle(dish);
        }
    }
}