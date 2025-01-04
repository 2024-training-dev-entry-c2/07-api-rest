package com.restaurant.management.services;

import com.restaurant.management.chain.DiscountHandler;
import com.restaurant.management.chain.FrequentClientDiscounttHandler;
import com.restaurant.management.chain.PopularDishPriceAdjustmentHandler;
import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Order;
import com.restaurant.management.observer.IObservable;
import com.restaurant.management.observer.IOrderObserver;
import com.restaurant.management.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IObservable {
  private final OrderRepository repository;
  private final List<IOrderObserver> observers;

  @Autowired
  public OrderService(OrderRepository repository, List<IOrderObserver> observers) {
    this.repository = repository;
    this.observers = observers;
  }

  public void addOrder(Order order){
    order.calculateTotal();
    repository.save(order);
    notifyObservers(order);
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
      o.calculateTotal();
      return repository.save(o);
    }).orElseThrow(()-> new RuntimeException("Pedido con id " + id + " no se pudo actualizar."));
  }

  public void deleteOrder(Long id){
    repository.deleteById(id);
  }

  public void applyDiscounts(Order order){
    DiscountHandler frequentHandler = new FrequentClientDiscounttHandler();
    DiscountHandler popularHandler = new PopularDishPriceAdjustmentHandler();

    frequentHandler.setNextHandler(popularHandler);
    order.setTotal(frequentHandler.applyDiscount(order));
  }

  @Override
  public void addObserver(IOrderObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(IOrderObserver observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers(Order order) {
    for(IOrderObserver observer : observers){
      for(Dish dish : order.getDishes()){
        observer.updateOrder(order.getClient(), dish);
      }
    }
  }
}
