package com.example.restaurant.handlers;

import com.example.restaurant.handlers.interfaces.IOrderHandler;
import com.example.restaurant.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProcessingChain {

    private final List<IOrderHandler> handlers;

    @Autowired
    public OrderProcessingChain(List<IOrderHandler> handlers) {
        this.handlers = handlers;
    }

    public void process(Order order) {
        handlers.forEach(handler -> handler.handle(order));
    }
}
