package restaurant_managment.Utils.Handlers;

import restaurant_managment.Models.OrderModel;

public class FinalPriceHandler implements PriceHandler {

  private PriceHandler nextHandler;

  @Override
  public void setNextHandler(PriceHandler nextHandler) {
    this.nextHandler = nextHandler;
  }

  @Override
  public Double handle(OrderModel order) {
    return order.getTotalPrice();
  }
}