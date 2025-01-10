package com.api_restaurant.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoDiscountStrategyTest {

    @Test
    void applyDiscount() {
        NoDiscountStrategy discountStrategy = new NoDiscountStrategy();
        double total = 100.0;
        double expectedTotal = 100.0;

        double actualTotal = discountStrategy.applyDiscount(total);

        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void applyDiscountWithZeroTotal() {
        NoDiscountStrategy discountStrategy = new NoDiscountStrategy();
        double total = 0.0;
        double expectedTotal = 0.0;

        double actualTotal = discountStrategy.applyDiscount(total);

        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void applyDiscountWithNegativeTotal() {
        NoDiscountStrategy discountStrategy = new NoDiscountStrategy();
        double total = -100.0;
        double expectedTotal = -100.0;

        double actualTotal = discountStrategy.applyDiscount(total);

        assertEquals(expectedTotal, actualTotal);
    }
}