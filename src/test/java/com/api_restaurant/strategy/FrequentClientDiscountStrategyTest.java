package com.api_restaurant.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrequentClientDiscountStrategyTest {

    @Test
    void applyDiscount() {
        FrequentClientDiscountStrategy discountStrategy = new FrequentClientDiscountStrategy();
        double total = 100.0;
        double expectedDiscountedTotal = total * (1 - 0.0238);

        double actualDiscountedTotal = discountStrategy.applyDiscount(total);

        assertEquals(expectedDiscountedTotal, actualDiscountedTotal, 0.001);
    }

    @Test
    void applyDiscountWithZeroTotal() {
        FrequentClientDiscountStrategy discountStrategy = new FrequentClientDiscountStrategy();
        double total = 0.0;
        double expectedDiscountedTotal = 0.0;

        double actualDiscountedTotal = discountStrategy.applyDiscount(total);

        assertEquals(expectedDiscountedTotal, actualDiscountedTotal, 0.001);
    }

    @Test
    void applyDiscountWithNegativeTotal() {
        FrequentClientDiscountStrategy discountStrategy = new FrequentClientDiscountStrategy();
        double total = -100.0;
        double expectedDiscountedTotal = total * (1 - 0.0238);

        double actualDiscountedTotal = discountStrategy.applyDiscount(total);

        assertEquals(expectedDiscountedTotal, actualDiscountedTotal, 0.001);
    }
}