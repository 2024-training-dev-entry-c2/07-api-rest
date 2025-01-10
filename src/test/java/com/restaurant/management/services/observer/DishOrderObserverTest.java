package com.restaurant.management.services.observer;

import com.restaurant.management.constants.DishStateEnum;
import com.restaurant.management.models.Dish;
import com.restaurant.management.repositories.DishRepository;
import com.restaurant.management.repositories.OrderDishRepository;
import com.restaurant.management.repositories.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DishOrderObserverTest {
  private final OrderDishRepository orderDishRepository;
  private final DishRepository dishRepository;
  private final DishOrderObserver dishOrderObserver;

  DishOrderObserverTest() {
    this.orderDishRepository = mock(OrderDishRepository.class);
    this.dishRepository = mock(DishRepository.class);
    this.dishOrderObserver = new DishOrderObserver(orderDishRepository, dishRepository);
  }

  @Test
  @DisplayName("Actualizar precio de plato cuando se agrega un pedido")
  void updateOrder() {
    Dish dish = new Dish(1L, "name", "description", 10f, null);
    when(orderDishRepository.sumQuantityByDish(dish)).thenReturn(101);

    dishOrderObserver.updateOrder(null, dish);

    assertEquals(DishStateEnum.POPULAR, dish.getState());

    float expectedPrice = 10f * 1.0573f;
    assertEquals(expectedPrice, dish.getPrice(), 0.0001f);


    verify(orderDishRepository).sumQuantityByDish(dish);
    verify(dishRepository).save(dish);
  }

  @Test
  @DisplayName("No actualizar precio de plato cuando se agrega un pedido")
  void updateOrderNotPopular() {
    Dish dish = new Dish(1L, "name", "description", 10f, null);
    when(orderDishRepository.sumQuantityByDish(dish)).thenReturn(100);

    dishOrderObserver.updateOrder(null, dish);

    assertEquals(DishStateEnum.NORMAL, dish.getState());
    assertEquals(10f, dish.getPrice(), 0.0001f);
  }

  @Test
  @DisplayName("No actualizar precio de plato cuando se agrega un pedido")
  void updateOrderAlreadyPopular() {
    Dish dish = new Dish(1L, "name", "description", 10f, null);
    dish.setState(DishStateEnum.POPULAR);
    when(orderDishRepository.sumQuantityByDish(dish)).thenReturn(104);

    dishOrderObserver.updateOrder(null, dish);

    assertEquals(DishStateEnum.POPULAR, dish.getState());
    float expectedPrice = 10f * 1.0573f;
    assertEquals(expectedPrice, dish.getPrice(), 0.0001f);
  }
}