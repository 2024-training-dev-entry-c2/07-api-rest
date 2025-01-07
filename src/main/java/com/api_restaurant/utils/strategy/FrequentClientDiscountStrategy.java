package com.api_restaurant.utils.strategy;

import org.springframework.stereotype.Component;

@Component
public class FrequentClientDiscountStrategy implements DiscountStrategy {
    private static final Double DISCOUNT_RATE = 0.0238;

    @Override
    public double applyDiscount(double total) {
        return total * (1 - DISCOUNT_RATE);
    }
}