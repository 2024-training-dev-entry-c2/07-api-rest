package com.api_restaurant.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegularPriceStrategyTest {

    @Test
    void applyPrice() {
        RegularPriceStrategy priceStrategy = new RegularPriceStrategy();
        double basePrice = 100.0;
        double expectedPrice = 100.0;

        double actualPrice = priceStrategy.applyPrice(basePrice);

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void applyPriceWithZeroBasePrice() {
        RegularPriceStrategy priceStrategy = new RegularPriceStrategy();
        double basePrice = 0.0;
        double expectedPrice = 0.0;

        double actualPrice = priceStrategy.applyPrice(basePrice);

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void applyPriceWithNegativeBasePrice() {
        RegularPriceStrategy priceStrategy = new RegularPriceStrategy();
        double basePrice = -100.0;
        double expectedPrice = -100.0;

        double actualPrice = priceStrategy.applyPrice(basePrice);

        assertEquals(expectedPrice, actualPrice);
    }
}