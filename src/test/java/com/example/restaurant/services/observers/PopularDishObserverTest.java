package com.example.restaurant.services.observers;

import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PopularDishObserverTest {

  @Mock
  private DishRepository dishRepository;

  private PopularDishObserver popularDishObserver;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    popularDishObserver = new PopularDishObserver(dishRepository);
  }

  @Test
  @DisplayName("Notificar observador de plato popular")
  void onOrderCreated() {
    Dish dish = new Dish();
    dish.setDishId(1L);
    dish.setName("Test Dish");

    Order order = new Order();
    order.setDishes(List.of(dish));

    when(dishRepository.countOrdersById(dish.getDishId())).thenReturn(100L);

    popularDishObserver.onOrderCreated(order);

    verify(dishRepository, times(1)).countOrdersById(dish.getDishId());
  }
}