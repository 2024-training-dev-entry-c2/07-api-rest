package com.restaurant.restaurant.chain;

import com.restaurant.restaurant.models.OrderModel;

public class OrderNotificationHandler extends OrderHandler {
  @Override
  protected void process(OrderModel order) {
    System.out.println("Order notification successful " + order.getId());
  }
}
