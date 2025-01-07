package com.restaurant.restaurant.chain;

import com.restaurant.restaurant.models.OrderModel;

public class OrderCostCalculationHandler extends OrderHandler {

  @Override
  protected void process(OrderModel order) {
    Double totalCost = order.getDishes().stream().mapToDouble(dish -> dish.getPrice()).sum();
    order.setTotalCost(totalCost);
    System.out.println("Order cost calculation successful " + order.getId());
  }
}
