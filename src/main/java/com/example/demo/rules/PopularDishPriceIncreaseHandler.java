package com.example.demo.rules;

import com.example.demo.models.Order;
import com.example.demo.strategy.PopularDishPricingStrategy;
import org.springframework.stereotype.Component;

@Component
public class PopularDishPriceIncreaseHandler implements OrderRule {
    private OrderRule nextHandler;
    private final PopularDishPricingStrategy strategy;

    public PopularDishPriceIncreaseHandler(PopularDishPricingStrategy strategy) {
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