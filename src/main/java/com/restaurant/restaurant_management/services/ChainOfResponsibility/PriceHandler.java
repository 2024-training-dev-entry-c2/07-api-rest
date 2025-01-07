package com.restaurant.restaurant_management.services.ChainOfResponsibility;

import com.restaurant.restaurant_management.models.ClientOrder;

public abstract class PriceHandler {
  protected PriceHandler nextHandler;

  public void setNextHandler(PriceHandler nextHandler) {
    this.nextHandler = nextHandler;
  }

  public abstract Double calculateTotal(ClientOrder order, Double total);
}
