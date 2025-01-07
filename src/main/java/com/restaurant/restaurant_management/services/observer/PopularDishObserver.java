package com.restaurant.restaurant_management.services.observer;

import com.restaurant.restaurant_management.constants.AppConstants;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.repositories.DishRepository;
import com.restaurant.restaurant_management.repositories.OrderDetailRepository;

public class PopularDishObserver implements IObserver {
  private final DishRepository dishRepository;
  private final OrderDetailRepository orderDetailRepository;

  public PopularDishObserver(DishRepository dishRepository, OrderDetailRepository orderDetailRepository) {
    this.dishRepository = dishRepository;
    this.orderDetailRepository = orderDetailRepository;
  }

  @Override
  public void update(String eventType, Object data) {
    if (eventType.equals("DishOrdered")) {
      Dish dish = (Dish) data;
      Long totalOrders = orderDetailRepository.countByDishId(dish.getId());
      if (totalOrders >= 100 && Boolean.TRUE.equals(!dish.getIsPopular())) {
        dish.setIsPopular(true);
        dish.setBasePrice((int) (dish.getBasePrice() * AppConstants.INCREASE)); // Incrementa 5.73%
        dishRepository.save(dish);
      }
    }
  }
}
