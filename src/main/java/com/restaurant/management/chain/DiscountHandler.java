package com.restaurant.management.chain;

import com.restaurant.management.models.Order;

public abstract class DiscountHandler {
  protected DiscountHandler nextHandler;

  public void setNextHandler(DiscountHandler nextHandler) {
    this.nextHandler = nextHandler;
  }

  public abstract float applyDiscount(Order order);

  public float passToNextHandler(Order order){
    if (nextHandler != null) {
      return nextHandler.applyDiscount(order);
    }
    return order.getTotal();
  }
}
