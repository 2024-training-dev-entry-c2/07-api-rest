package com.example.restaurant.services.handlers;

import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PopularDishMarkupHandlerTest {

  @Mock
  private DishRepository dishRepository;

  private PopularDishMarkupHandler popularDishMarkupHandler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    popularDishMarkupHandler = new PopularDishMarkupHandler(dishRepository);
  }

  @Test
  @DisplayName("Calcular precio con markup para plato popular")
  void calculatePriceWithMarkup() {
    Dish dish = new Dish();
    dish.setDishId(1L);
    Order order = new Order();
    Float initialPrice = 100.0f;

    when(dishRepository.countOrdersByDishId(dish.getDishId())).thenReturn(100L);

    Float result = popularDishMarkupHandler.calculatePrice(initialPrice, order, dish);

    assertEquals(105.73f, result, 0.01);
    verify(dishRepository, times(1)).countOrdersByDishId(dish.getDishId());
  }

  @Test
  @DisplayName("Calcular precio sin markup para plato no popular")
  void calculatePriceWithoutMarkup() {
    Dish dish = new Dish();
    dish.setDishId(1L);
    Order order = new Order();
    Float initialPrice = 100.0f;

    when(dishRepository.countOrdersByDishId(dish.getDishId())).thenReturn(50L);

    Float result = popularDishMarkupHandler.calculatePrice(initialPrice, order, dish);

    assertEquals(100.0f, result);
    verify(dishRepository, times(1)).countOrdersByDishId(dish.getDishId());
  }
}