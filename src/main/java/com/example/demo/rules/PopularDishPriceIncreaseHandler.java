package com.example.demo.rules;

import com.example.demo.models.Order;
import com.example.demo.strategy.PricingStrategy;

public class PopularDishPriceIncreaseHandler implements OrderRule {
    private OrderRule nextHandler;
    private final PricingStrategy strategy;

    public PopularDishPriceIncreaseHandler(PricingStrategy strategy) {
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