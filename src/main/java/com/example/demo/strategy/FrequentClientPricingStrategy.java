package com.example.demo.strategy;

import com.example.demo.models.Order;
import org.springframework.stereotype.Component;

@Component
public class FrequentClientPricingStrategy implements PricingStrategy {
    @Override
    public void apply(Order order) {
        if (order.getClient().getIsOften()) {
            double discount = order.getTotalPrice() * 0.0238;
            order.setTotalPrice(order.getTotalPrice() - discount);
        }
    }
}
