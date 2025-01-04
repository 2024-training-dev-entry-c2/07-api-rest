package com.restaurant.management.services;

import com.restaurant.management.models.Order;
import com.restaurant.management.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class OrderService {
  private final OrderRepository repository;

  @Autowired
  public OrderService(OrderRepository repository) {
    this.repository = repository;
  }

  public void addOrder(Order order){
    repository.save(order);
  }

  public Optional<Order> getOrderById(Long id){
    return repository.findById(id);
  }

  public List<Order> getOrders(){
    return repository.findAll();
  }

  public Order updateOrder(Long id, Order updatedOrder){
    return repository.findById(id).map(o ->{
      o.setClient(updatedOrder.getClient());
      o.setDishes(updatedOrder.getDishes());
      return repository.save(o);
    }).orElseThrow(()-> new RuntimeException("Pedido con id " + id + " no se pudo actualizar."));
  }

  public void deleteOrder(Long id){
    repository.deleteById(id);
  }
}
