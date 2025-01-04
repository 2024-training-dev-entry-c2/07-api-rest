package com.restaurant.management.services.observer;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.Dish;
import com.restaurant.management.observer.IOrderObserver;
import com.restaurant.management.repositories.DishRepository;
import com.restaurant.management.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishOrderObserver implements IOrderObserver {
  private final OrderRepository orderRepository;
  private final DishRepository dishRepository;

  @Autowired
  public DishOrderObserver(OrderRepository orderRepository, DishRepository dishRepository) {
    this.orderRepository = orderRepository;
    this.dishRepository = dishRepository;
  }

  @Override
  public void updateOrder(Client client, Dish dish) {
    int dishCount = orderRepository.countByDishesContaining(dish);
    if (dishCount > 100 && !dish.getPopular()) {
      dish.setPopular(true);
      dishRepository.save(dish);
    }
  }
}
