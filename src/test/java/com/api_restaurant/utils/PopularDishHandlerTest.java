package com.api_restaurant.utils;

import com.api_restaurant.models.Dish;
import com.api_restaurant.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PopularDishHandlerTest {

    private PopularDishHandler popularDishHandler;
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        popularDishHandler = new PopularDishHandler(orderRepository);
    }

    @Test
    @DisplayName("Handle - Dish Becomes Special")
    void handleDishBecomesSpecial() {
        Dish dish = new Dish();
        dish.setId(1L);

        when(orderRepository.countByDishId(1L)).thenReturn(101L);

        popularDishHandler.handle(dish);

        assertTrue(dish.getSpecialDish());

        verify(orderRepository).countByDishId(1L);
    }

    @Test
    @DisplayName("Handle - Dish Does Not Become Special")
    void handleDishDoesNotBecomeSpecial() {
        Dish dish = new Dish();
        dish.setId(1L);

        when(orderRepository.countByDishId(1L)).thenReturn(50L);

        popularDishHandler.handle(dish);

        assertFalse(dish.getSpecialDish());

        verify(orderRepository).countByDishId(1L);
    }

    @Test
    @DisplayName("Set Next Handler")
    void setNext() {
        DishHandler nextHandler = mock(DishHandler.class);
        popularDishHandler.setNext(nextHandler);

        Dish dish = new Dish();
        popularDishHandler.handle(dish);

        verify(nextHandler).handle(dish);
    }
}