package com.example.demo.strategy;

import com.example.demo.models.Order;

public interface PricingStrategy {
    void apply(Order order);
}
