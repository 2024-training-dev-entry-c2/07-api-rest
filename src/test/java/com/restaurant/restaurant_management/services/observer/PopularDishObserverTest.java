package com.restaurant.restaurant_management.services.observer;

import com.restaurant.restaurant_management.constants.AppConstants;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.repositories.DishRepository;
import com.restaurant.restaurant_management.repositories.OrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PopularDishObserverTest {
  private DishRepository dishRepository;
  private OrderDetailRepository orderDetailRepository;
  private PopularDishObserver popularDishObserver;

  @BeforeEach
  void setUp() {
    dishRepository = mock(DishRepository.class);
    orderDetailRepository = mock(OrderDetailRepository.class);
    popularDishObserver = new PopularDishObserver(dishRepository, orderDetailRepository);
  }

  @Test
  @DisplayName("Update Dish")
  void testMarkDishAsPopular() {
    Dish dish = new Dish();
    dish.setId(1);
    dish.setIsPopular(false);
    dish.setBasePrice(45000);

    when(orderDetailRepository.countByDishId(1)).thenReturn(100L);
    popularDishObserver.update("DishOrdered", dish);

    Mockito.verify(dishRepository, Mockito.times(1)).save(dish);
    assert dish.getIsPopular();
    assert dish.getBasePrice() == (int)(45000 * AppConstants.INCREASE);
  }

  @Test
  @DisplayName("Update Dish - Already Popular")
  void testNoUpdateIfDishIsAlreadyPopular() {
    Dish dish = new Dish();
    dish.setId(1);
    dish.setIsPopular(true);
    dish.setBasePrice(45000);

    when(orderDetailRepository.countByDishId(1)).thenReturn(100L);
    popularDishObserver.update("DishOrdered", dish);

    Mockito.verify(dishRepository, Mockito.never()).save(any());
  }

  @Test
  @DisplayName("Update Dish - Less than 100 orders")
  void testNoUpdateIfLessThan100Orders() {
    Dish dish = new Dish();
    dish.setId(1);
    dish.setIsPopular(false);
    dish.setBasePrice(45000);

    when(orderDetailRepository.countByDishId(1)).thenReturn(50L);
    popularDishObserver.update("DishOrdered", dish);

    Mockito.verify(dishRepository, Mockito.never()).save(any());
    assert !dish.getIsPopular();
  }

  @Test
  @DisplayName("Update Dish - Different Event Type")
  void testNoActionForDifferentEventType() {
    Dish dish = new Dish();
    dish.setId(1);
    dish.setIsPopular(false);
    dish.setBasePrice(45000);

    when(orderDetailRepository.countByDishId(1)).thenReturn(100L);
    popularDishObserver.update("OtherEvent", dish);

    Mockito.verify(dishRepository, Mockito.never()).save(any());
    assert !dish.getIsPopular();
  }
}