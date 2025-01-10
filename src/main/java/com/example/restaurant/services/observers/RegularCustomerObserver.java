package com.example.restaurant.services.observers;

import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegularCustomerObserver implements OrderObserver {

  private static final Logger log = LogManager.getLogger(RegularCustomerObserver.class);
  private final OrderRepository orderRepository;

  @Override
  public void onOrderCreated(Order order) {
    Long orderQuantity = orderRepository.countByCustomerCustomerId(order.getCustomer().getCustomerId());
    Boolean isRegularCustomer = orderQuantity >= 10;
    log.info("{}{}Es cliente regular (ha ordenado más de 10 veces): ", isRegularCustomer, order.getCustomer().getName());
  }
}
