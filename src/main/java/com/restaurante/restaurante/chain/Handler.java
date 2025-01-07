package com.restaurante.restaurante.chain;

import com.restaurante.restaurante.models.Orders;

public abstract class Handler {
    protected Handler nextHandler;

    public Handler setNext(Handler handler) {
        this.nextHandler = handler;
        return handler;
    }

    public abstract void process(Orders order);
}
