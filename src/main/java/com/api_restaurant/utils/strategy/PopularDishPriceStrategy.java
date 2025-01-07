package com.api_restaurant.utils.strategy;

import org.springframework.stereotype.Component;

@Component
public class PopularDishPriceStrategy implements PriceStrategy {
    private static final double PRICE_INCREASE_RATE = 0.0573;

    @Override
    public double applyPrice(double basePrice) {
        return basePrice * (1 + PRICE_INCREASE_RATE);
    }
}