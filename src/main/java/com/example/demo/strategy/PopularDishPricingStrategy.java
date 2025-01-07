package com.example.demo.strategy;

import com.example.demo.models.Dishfood;
import com.example.demo.models.Order;

public class PopularDishPricingStrategy implements PricingStrategy {
    @Override
    public void apply(Order order) {
        double increase = order.getDishfoods().stream()
                .filter(Dishfood::getIsPopular)
                .mapToDouble(dish -> dish.getPrice() * 0.0573)
                .sum();
        order.setTotal(order.getTotal() + increase);
    }
}