package restaurant_managment.Utils.Handlers;

import restaurant_managment.Models.OrderModel;

public interface PriceHandler {
  void setNextHandler(PriceHandler nextHandler);
  Double handle(OrderModel order);
}