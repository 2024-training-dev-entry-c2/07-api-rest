package com.example.demo.strategy;

import com.example.demo.models.Order;

public class FrequentClientPricingStrategy implements PricingStrategy {
    @Override
    public void apply(Order order) {
        if (order.getClient().getIsOften()) {
            double discount = order.getTotal() * 0.0238;
            order.setTotal(order.getTotal() - discount);
        }
    }
}
