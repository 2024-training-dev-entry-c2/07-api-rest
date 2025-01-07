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
    if (orderQuantity >= 10) {
      //cliente frecuente, cadena de responsabilidad o logica de descuento
    }
  }
}
