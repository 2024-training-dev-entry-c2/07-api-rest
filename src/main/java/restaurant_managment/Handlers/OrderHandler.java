package restaurant_managment.Handlers;

import restaurant_managment.Models.OrderModel;

public abstract class OrderHandler implements IOrderHandler {
  protected IOrderHandler nextHandler;

  @Override
  public void setNextHandler(IOrderHandler nextHandler) {
    this.nextHandler = nextHandler;
  }

  @Override
  public void handle(OrderModel order) {
    if (nextHandler != null) {
      nextHandler.handle(order);
    }
  }
}