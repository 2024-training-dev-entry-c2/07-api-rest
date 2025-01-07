package com.example.restaurant.services.observers;

import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PopularDishObserver implements OrderObserver {

  private static final Logger log = LogManager.getLogger(PopularDishObserver.class);
  private final DishRepository dishRepository;

  @Override
  public void onOrderCreated(Order order) {
    for (Dish dish : order.getDishes()) {
      Long dishOrdersQuantity = dishRepository.countOrdersById(dish.getId());
      Boolean isPopularDish = dishOrdersQuantity >= 100;
      log.info("{}{} es popular (ha sido ordenado más de 100 veces)", isPopularDish, dish.getName());
    }
  }
}
