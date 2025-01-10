package com.example.demo.rules;

import com.example.demo.models.Order;
import com.example.demo.strategy.FrequentClientPricingStrategy;
import org.springframework.stereotype.Component;

@Component
public class FrequentClientDiscountHandler implements OrderRule {
    private OrderRule nextHandler;
    private final FrequentClientPricingStrategy strategy; // Usa la implementación concreta

    public FrequentClientDiscountHandler(FrequentClientPricingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void setNextHandler(OrderRule handler) {
        this.nextHandler = handler;
    }

    @Override
    public void applyRule(Order order) {
        strategy.apply(order);
        if (nextHandler != null) {
            nextHandler.applyRule(order);
        }
    }
}