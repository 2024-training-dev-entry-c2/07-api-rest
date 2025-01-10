package com.example.demo.services;

import com.example.demo.models.Dishfood;
import com.example.demo.models.Order;
import com.example.demo.repositories.DishfoodRepository;
import com.example.demo.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DishfoodValidationServiceTest {
    @Mock
    private DishfoodRepository dishfoodRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private NotificationService notificationService;

    private DishfoodValidationService dishfoodValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dishfoodValidationService = new DishfoodValidationService(dishfoodRepository, orderRepository, notificationService);
    }

    @Test
    void checkDishFood_WhenDishBecomesPopular_ShouldUpdateDishAndNotify() {

        Dishfood dish = Dishfood.builder()
                .id(1L)
                .name("Pizza")
                .price(15.0)
                .isPopular(false)
                .build();
        Order order = new Order(1L, null, null, List.of(dish));

        when(orderRepository.countByDishfoods_Id(1L)).thenReturn(12L);

        dishfoodValidationService.checkDishFood(order);

        verify(dishfoodRepository, times(1)).save(dish);
        verify(notificationService, times(1)).notifyObserversdish(order);
        assertTrue(dish.getIsPopular());
    }

    @Test
    void checkDishFood_WhenDishNotPopularAndThresholdNotMet_ShouldNotUpdateDish() {
        // Arrange
        Dishfood dish = Dishfood.builder()
                .id(2L)
                .name("Burger")
                .price(10.0)
                .isPopular(false)
                .build();
        Order order = new Order(1L, null, null, List.of(dish));

        when(orderRepository.countByDishfoods_Id(2L)).thenReturn(5L);

        // Act
        dishfoodValidationService.checkDishFood(order);

        // Assert
        verify(dishfoodRepository, never()).save(any());
        verify(notificationService, never()).notifyObserversdish(order);
        assertFalse(dish.getIsPopular());
    }

    @Test
    void checkDishFood_WhenDishAlreadyPopular_ShouldNotNotifyOrUpdate() {
        Dishfood dish = Dishfood.builder()
                .id(3L)
                .name("Sushi")
                .price(20.0)
                .isPopular(true)
                .build();
        Order order = new Order(1L, null, null, List.of(dish));

        when(orderRepository.countByDishfoods_Id(3L)).thenReturn(15L);


        dishfoodValidationService.checkDishFood(order);


        verify(dishfoodRepository, never()).save(any());
        verify(notificationService, never()).notifyObserversdish(order);
        assertTrue(dish.getIsPopular());
    }

    @Test
    void checkDishFood_WhenOrderHasMultipleDishes_ShouldOnlyNotifyForNewPopularDishes() {
        // Arrange
        Dishfood dish1 = Dishfood.builder()
                .id(4L)
                .name("Pasta")
                .price(12.0)
                .isPopular(false)
                .build();

        Dishfood dish2 = Dishfood.builder()
                .id(5L)
                .name("Salad")
                .price(8.0)
                .isPopular(true)
                .build();

        Order order = new Order(1L, null, null, List.of(dish1, dish2));

        when(orderRepository.countByDishfoods_Id(4L)).thenReturn(13L);
        when(orderRepository.countByDishfoods_Id(5L)).thenReturn(20L);

        // Act
        dishfoodValidationService.checkDishFood(order);

        // Assert
        verify(dishfoodRepository, times(1)).save(dish1);
        verify(notificationService, times(1)).notifyObserversdish(order);
        assertTrue(dish1.getIsPopular());
        assertTrue(dish2.getIsPopular());
    }

}