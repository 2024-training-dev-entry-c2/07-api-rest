package com.api_restaurant.utils;

import com.api_restaurant.models.Dish;

public interface DishHandler {
    void setNext(DishHandler handler);
    void handle(Dish dish);
}

