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
    Double totalPrice = calculateTotalPrice(order);
    order.setTotalPrice(totalPrice);
    return handleNext(order, totalPrice);
  }

  private Double calculateTotalPrice(OrderModel order) {
    Double totalPrice = order.getTotalPrice();
    for (DishModel dish : order.getDishes()) {
      totalPrice += applyPriceAdjustment(dish);
    }
    return totalPrice;
  }

  private Double applyPriceAdjustment(DishModel dish) {
    if (dish.getIsPopular()) {
      return dish.getPrice() * 0.0573;
    }
    return 0.0;
  }

  private Double handleNext(OrderModel order, Double totalPrice) {
    if (nextHandler != null) {
      return nextHandler.handle(order);
    }
    return totalPrice;
  }
}