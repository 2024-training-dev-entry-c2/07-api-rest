package com.restaurante.restaurante.services;


import com.restaurante.restaurante.models.Dish;

import java.util.List;

public interface IDishOrderService {
    List<Dish> processOrderDishes(List<Dish> orderDishes);
}
