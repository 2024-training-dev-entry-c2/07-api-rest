package com.example.restaurant.services.observers;

import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegularCustomerObserver implements OrderObserver {

  private final OrderRepository orderRepository;

  @Override
  public void onOrderCreated(Order order) {
    Long orderQuantity =  orderRepository.countByCustomerId(order.getCustomer().getId());
    Boolean isRegularCustomer = orderQuantity >= 10;
    System.out.println(isRegularCustomer + order.getCustomer().getName() + " es cliente regular (ha ordenado más de 10 veces): ");
  }
}
