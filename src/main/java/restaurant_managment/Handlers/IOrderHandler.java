package restaurant_managment.Handlers;

import restaurant_managment.Models.OrderModel;

public interface IOrderHandler {
  void setNextHandler(IOrderHandler nextHandler);
  void handle(OrderModel order);
}