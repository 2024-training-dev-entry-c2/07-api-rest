package restaurant_managment.Utils.Handlers;

import restaurant_managment.Models.OrderModel;
import restaurant_managment.Models.DishModel;

public class PopularDishPriceAdjustmentHandler implements PriceHandler {

  private PriceHandler nextHandler;

  @Override
  public void setNextHandler(PriceHandler nextHandler) {
    this.nextHandler = nextHandler;
  }

  @Override
  public Double handle(OrderModel order) {
    Double totalPrice = order.getTotalPrice();

    for (DishModel dish : order.getDishes()) {
      if (dish.getIsPopular()) {
        totalPrice += dish.getPrice() * 0.0573;
      }
    }

    if (nextHandler != null) {
      order.setTotalPrice(totalPrice);
      return nextHandler.handle(order);
    }

    return totalPrice;
  }
}