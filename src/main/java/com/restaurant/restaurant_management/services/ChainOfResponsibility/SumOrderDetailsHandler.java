package com.restaurant.restaurant_management.services.ChainOfResponsibility;

import com.restaurant.restaurant_management.models.ClientOrder;
import com.restaurant.restaurant_management.models.OrderDetail;

public class SumOrderDetailsHandler extends PriceHandler {
  @Override
  public Double calculateTotal(ClientOrder order, Double currentTotal) {
    for (OrderDetail orderDetail : order.getOrderDetails()) {
      currentTotal += orderDetail.getSubtotal();
    }
    if (nextHandler != null) {
      return nextHandler.calculateTotal(order, currentTotal);
    }
    return currentTotal;
  }
}
