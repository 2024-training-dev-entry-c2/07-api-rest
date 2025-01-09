package restaurant_managment.Utils.Handlers;

import restaurant_managment.Models.OrderModel;
import restaurant_managment.Models.CustomerModel;

public class FrequentCustomerDiscountHandler implements PriceHandler {

  private PriceHandler nextHandler;

  @Override
  public void setNextHandler(PriceHandler nextHandler) {
    this.nextHandler = nextHandler;
  }

  @Override
  public Double handle(OrderModel order) {
    CustomerModel customer = order.getReservation().getCustomer();
    Double totalPrice = order.getTotalPrice();

    if (customer.getIsFrequent()) {
      totalPrice = totalPrice * 0.9762;
    }

    order.setTotalPrice(totalPrice);

    if (nextHandler != null) {
      return nextHandler.handle(order);
    }

    return totalPrice;
  }
}