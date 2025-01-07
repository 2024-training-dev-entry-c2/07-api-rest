package com.example.restaurant.services.handlers;

import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;

public abstract class AbstractPriceHandler implements PriceHandler {
    protected PriceHandler nextHandler;

    @Override
    public void setNextHandler(PriceHandler handler) {
        this.nextHandler = handler;
    }

    protected Float applyNext(Float price, Order order, Dish dish) {
        return nextHandler != null ? nextHandler.calculatePrice(price, order, dish) : price;
    }
}
