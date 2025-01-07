package com.restaurant.restaurant_management.services.chainOfResponsibility;

import com.restaurant.restaurant_management.constants.AppConstants;
import com.restaurant.restaurant_management.models.ClientOrder;

public class FrequentClientDiscountHandler extends PriceHandler {
  @Override
  public Double calculateTotal(ClientOrder order, Double currentTotal) {
    if (Boolean.TRUE.equals(order.getClient().getIsFrequent())) {
      currentTotal *= AppConstants.DISCOUNT;
    }
    if (nextHandler != null) {
      return nextHandler.calculateTotal(order, currentTotal);
    }
    return currentTotal;
  }
}
