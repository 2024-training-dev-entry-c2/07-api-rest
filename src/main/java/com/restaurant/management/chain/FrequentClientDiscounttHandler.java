package com.restaurant.management.chain;

import com.restaurant.management.models.Order;

public class FrequentClientDiscounttHandler extends DiscountHandler{
  @Override
  public float applyDiscount(Order order) {
    if (Boolean.TRUE.equals(order.getClient().getFrequent())) {
      float discount = order.getTotal() * 0.0238f;
      order.setTotal(order.getTotal() - discount);
    }
    return passToNextHandler(order);
  }
}
