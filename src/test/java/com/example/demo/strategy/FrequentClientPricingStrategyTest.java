package com.example.demo.strategy;

import com.example.demo.models.Client;
import com.example.demo.models.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FrequentClientPricingStrategyTest {

    @Test
    void apply_ShouldApplyDiscountForFrequentClient() {

        Client client = new Client();
        client.setIsOften(true);


        Order order = new Order();
        order.setClient(client);
        order.setTotalPrice(100.0);


        FrequentClientPricingStrategy strategy = new FrequentClientPricingStrategy();


        strategy.apply(order);


        double expectedPrice = 100.0 - (100.0 * 0.0238);
        assertEquals(expectedPrice, order.getTotalPrice(), 0.0001);
    }

    @Test
    void apply_ShouldNotApplyDiscountForNonFrequentClient() {

        Client client = new Client();
        client.setIsOften(false);


        Order order = new Order();
        order.setClient(client);
        order.setTotalPrice(100.0);


        FrequentClientPricingStrategy strategy = new FrequentClientPricingStrategy();


        strategy.apply(order);


        assertEquals(100.0, order.getTotalPrice());
    }
}