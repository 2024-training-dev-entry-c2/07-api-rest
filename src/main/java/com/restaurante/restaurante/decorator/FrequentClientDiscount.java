package com.restaurante.restaurante.decorator;

public class FrequentClientDiscount extends OrderPriceDecorator {
    private static final Double DISCOUNT_PERCENTAGE = 2.38;

    public FrequentClientDiscount(OrderPrice decoratedPrice) {
        super(decoratedPrice);
    }

    @Override
    public Double calculatePrice() {
        Double originalPrice = decoratedOrder.calculatePrice();
        return originalPrice - (originalPrice * DISCOUNT_PERCENTAGE / 100);
    }
}