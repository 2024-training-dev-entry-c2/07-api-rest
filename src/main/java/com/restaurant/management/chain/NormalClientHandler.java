package com.restaurant.management.chain;

import com.restaurant.management.models.Order;

public class NormalClientHandler extends DiscountHandler {
  @Override
  public float applyDiscount(Order order) {
    return passToNextHandler(order);
  }
}
