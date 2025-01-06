package com.restaurant.restaurant.services;

import com.restaurant.restaurant.models.OrderModel;
import com.restaurant.restaurant.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;

  public OrderModel createOrder(OrderModel order) {
    return orderRepository.save(order);
  }

  public List<OrderModel> getOrders() {
    return orderRepository.findAll();
  }

  public OrderModel updateOrder(Long id, OrderModel order) {
    return orderRepository.findById(id).map(x -> {
      x.setClient(order.getClient());
      x.setDishes(order.getDishes());
      x.setDate(order.getDate());
      return orderRepository.save(x);
    }).orElseThrow(() -> new RuntimeException("Order with id " + id + " not found"));
  }

  public void deleteOrder(Long id) {
    orderRepository.deleteById(id);
  }
}
