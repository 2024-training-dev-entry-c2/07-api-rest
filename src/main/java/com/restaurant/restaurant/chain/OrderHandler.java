package com.restaurant.restaurant.chain;

import com.restaurant.restaurant.models.OrderModel;

public abstract class OrderHandler {
  private OrderHandler nextHandler;

  public void setNextHandler(OrderHandler nextHandler) {
    this.nextHandler = nextHandler;
  }

  public void handle(OrderModel order) {
    process(order);
    if(nextHandler != null) {
      nextHandler.handle(order);
    }
  }

    protected abstract void process(OrderModel order);
}
