package com.example.restaurant.services.handlers;

import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PopularDishMarkupHandler extends AbstractPriceHandler {
  private final DishRepository dishRepository;

  @Override
  public Float calculatePrice(Float price, Order order, Dish dish) {
    Long dishOrdersCount = dishRepository.countOrdersByDishId(dish.getId());
    if (dishOrdersCount >= 100) {
      price *= 1.0573f;
    }
    return applyNext(price, order, dish);
  }
}
