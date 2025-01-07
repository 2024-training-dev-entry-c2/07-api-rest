package com.example.restaurant.services.observers;

import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PopularDishObserver implements OrderObserver {

  private final DishRepository dishRepository;

  @Override
  public void onOrderCreated(Order order) {
    for(Dish dish : order.getDishes()) {
      Long dishOrdersQuantity = dishRepository.countOrdersByDishId(dish.getId());
      if (dishOrdersQuantity >= 100) {
        // platillo popular y logica de sobrecosto o cadena de responsabilidad
      }
    }
  }
}
