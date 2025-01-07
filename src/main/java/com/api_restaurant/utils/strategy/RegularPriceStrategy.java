package com.api_restaurant.utils.strategy;

import org.springframework.stereotype.Component;

@Component
public class RegularPriceStrategy implements PriceStrategy {
    @Override
    public double applyPrice(double basePrice) {
        return basePrice;
    }
}