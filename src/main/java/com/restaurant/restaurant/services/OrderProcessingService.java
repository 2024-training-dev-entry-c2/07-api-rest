package com.restaurant.restaurant.services;

import com.restaurant.restaurant.chain.OrderCostCalculationHandler;
import com.restaurant.restaurant.chain.OrderHandler;
import com.restaurant.restaurant.chain.OrderNotificationHandler;
import com.restaurant.restaurant.chain.OrderValidationHandler;
import com.restaurant.restaurant.models.OrderModel;

public class OrderProcessingService {
  private final OrderHandler chain;

  public OrderProcessingService() {

    OrderHandler validationHandler = new OrderValidationHandler();
    OrderHandler costCalculationHandler = new OrderCostCalculationHandler();
    OrderHandler notificationHandler = new OrderNotificationHandler();

    validationHandler.setNextHandler(costCalculationHandler);
    costCalculationHandler.setNextHandler(notificationHandler);

    this.chain = validationHandler;
  }

  public void processOrder(OrderModel order) {
    chain.handle(order);
  }
}
