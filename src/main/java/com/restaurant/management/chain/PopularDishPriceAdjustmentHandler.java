package com.restaurant.management.chain;

import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Order;

public class PopularDishPriceAdjustmentHandler extends DiscountHandler{
  @Override
  public float applyDiscount(Order order) {
    for (Dish dish : order.getDishes()) {
      if (dish.getPopular()) {
        float newPrice = dish.getPrice() * 1.0573f;
        dish.setPrice(newPrice);
      }
    }
    return passToNextHandler(order);
  }
}
