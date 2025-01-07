package com.restaurante.restaurante.decorator;

public class PopularDishPriceIncrease extends OrderPriceDecorator {
    private static final Double INCREASE_PERCENTAGE = 5.73;

    public PopularDishPriceIncrease(OrderPrice order) {
        super(order);
    }

    @Override
    public Double calculatePrice() {
        return decoratedOrder.calculatePrice() * (1 + INCREASE_PERCENTAGE / 100);
    }
}