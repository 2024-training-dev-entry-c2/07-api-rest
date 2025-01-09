package com.api_restaurant.strategy;

import org.springframework.stereotype.Component;

@Component
public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double total) {
        return total;
    }
}