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
    for (Dish dish : order.getDishes()) {
      Long dishOrdersQuantity = dishRepository.countOrdersByDishId(dish.getId());
      Boolean isPopularDish = dishOrdersQuantity >= 100;
      System.out.println(isPopularDish + dish.getName() + " es popular (ha sido ordenado más de 100 veces)");
    }
  }
}
