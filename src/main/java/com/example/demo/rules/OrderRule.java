package com.example.demo.rules;

import com.example.demo.models.Order;

public interface OrderRule {
    void setNextHandler(OrderRule nextRule);
    void applyRule(Order order);
}