package com.restaurant.restaurant_management.services.ChainOfResponsibility;

import com.restaurant.restaurant_management.models.ClientOrder;

public class FrequentClientDiscountHandler extends PriceHandler {
  @Override
  public Double calculateTotal(ClientOrder order, Double currentTotal) {
    if (Boolean.TRUE.equals(order.getClient().getIsFrequent())) {
      currentTotal *= 0.9762; // Descuento del 2.38%
      System.out.println("currentTotal2"+currentTotal);
    }
    if (nextHandler != null) {
      return nextHandler.calculateTotal(order, currentTotal);
    }
    return currentTotal;
  }
}
