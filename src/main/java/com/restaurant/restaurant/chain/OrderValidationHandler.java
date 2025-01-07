package com.restaurant.restaurant.chain;

import com.restaurant.restaurant.models.OrderModel;

public class OrderValidationHandler extends OrderHandler {

  @Override
  protected void process(OrderModel order) {
    if(order.getDishes() == null || order.getDishes().isEmpty()) {
      throw new RuntimeException("Order must contain at least one dish");
    }
    System.out.println("Order validation successful " + order.getId());
  }
}
