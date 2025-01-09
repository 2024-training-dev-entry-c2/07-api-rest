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
    Double totalPrice = calculateTotalPrice(order, customer);
    order.setTotalPrice(totalPrice);
    return handleNext(order, totalPrice);
  }

  private Double calculateTotalPrice(OrderModel order, CustomerModel customer) {
    Double totalPrice = order.getTotalPrice();
    if (customer.getIsFrequent()) {
      totalPrice = applyFrequentCustomerDiscount(totalPrice);
    }
    return totalPrice;
  }

  private Double applyFrequentCustomerDiscount(Double totalPrice) {
    return totalPrice * 0.9762;
  }

  private Double handleNext(OrderModel order, Double totalPrice) {
    if (nextHandler != null) {
      return nextHandler.handle(order);
    }
    return totalPrice;
  }
}