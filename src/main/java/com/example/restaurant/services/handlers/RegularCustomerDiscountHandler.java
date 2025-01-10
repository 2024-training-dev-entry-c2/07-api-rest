package com.example.restaurant.services.handlers;


import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegularCustomerDiscountHandler extends AbstractPriceHandler {
  private final OrderRepository orderRepository;

  @Override
  public Float calculatePrice(Float price, Order order, Dish dish) {
    Long customerOrdersQuantity = orderRepository.countByCustomerId(order.getCustomer().getCustomerId());
    if (customerOrdersQuantity >= 10) {
      return price * 0.9762f;
    }
    return applyNext(price, order, dish);
  }

}
