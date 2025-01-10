package com.api_restaurant.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopularDishPriceStrategyTest {

    @Test
    void applyPrice() {
        PopularDishPriceStrategy priceStrategy = new PopularDishPriceStrategy();
        double basePrice = 100.0;
        double expectedPrice = basePrice * (1 + 0.0573);

        double actualPrice = priceStrategy.applyPrice(basePrice);

        assertEquals(expectedPrice, actualPrice, 0.001);
    }

    @Test
    void applyPriceWithZeroBasePrice() {
        PopularDishPriceStrategy priceStrategy = new PopularDishPriceStrategy();
        double basePrice = 0.0;
        double expectedPrice = 0.0;

        double actualPrice = priceStrategy.applyPrice(basePrice);

        assertEquals(expectedPrice, actualPrice, 0.001);
    }

    @Test
    void applyPriceWithNegativeBasePrice() {
        PopularDishPriceStrategy priceStrategy = new PopularDishPriceStrategy();
        double basePrice = -100.0;
        double expectedPrice = basePrice * (1 + 0.0573);

        double actualPrice = priceStrategy.applyPrice(basePrice);

        assertEquals(expectedPrice, actualPrice, 0.001);
    }
}