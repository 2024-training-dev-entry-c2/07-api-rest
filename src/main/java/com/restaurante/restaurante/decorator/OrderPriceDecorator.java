package com.restaurante.restaurante.decorator;

public abstract class OrderPriceDecorator implements OrderPrice {
    protected OrderPrice decoratedOrder;

    public OrderPriceDecorator(OrderPrice order) {
        this.decoratedOrder = order;
    }
}