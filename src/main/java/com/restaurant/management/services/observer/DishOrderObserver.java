package com.restaurant.management.services.observer;

import com.restaurant.management.constants.DishStateEnum;
import com.restaurant.management.models.Client;
import com.restaurant.management.models.Dish;
import com.restaurant.management.observer.IOrderObserver;
import com.restaurant.management.repositories.DishRepository;
import com.restaurant.management.repositories.OrderDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishOrderObserver implements IOrderObserver {
  private final OrderDishRepository orderDishRepository;
  private final DishRepository dishRepository;

  @Autowired
  public DishOrderObserver(OrderDishRepository orderDishRepository, DishRepository dishRepository) {
    this.orderDishRepository = orderDishRepository;
    this.dishRepository = dishRepository;
  }

  @Override
  public void updateOrder(Client client, Dish dish) {
    int dishCount = orderDishRepository.sumQuantityByDish(dish);
    if (dishCount > 100 && dish.getState().equals(DishStateEnum.NORMAL)) {
      dish.setState(DishStateEnum.POPULAR);
      dishRepository.save(dish);
    }
  }
}
