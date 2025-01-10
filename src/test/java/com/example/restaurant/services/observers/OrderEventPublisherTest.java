package com.example.restaurant.services.observers;

import com.example.restaurant.models.Customer;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OrderEventPublisherTest {

  private OrderEventPublisher orderEventPublisher;
  private OrderObserver observer;

  @BeforeEach
  void setUp() {
    orderEventPublisher = new OrderEventPublisher();
    observer = mock(OrderObserver.class);
  }

  @Test
  @DisplayName("Registrar observador")
  void registerObserver() {
    orderEventPublisher.registerObserver(observer);
    orderEventPublisher.notifyObservers(new Order());
    verify(observer, times(1)).onOrderCreated(any(Order.class));
  }

  @Test
  @DisplayName("Notificar observadores")
  void notifyObservers() {

    Customer customer = new Customer(
            1L,
            "John ",
            "Doe",
            "emailJohn@hotmail.com",
            "+1 234"
    );

    Date date = new Date();

    Order order = new Order(
            1L,
            date,
            customer,
            getDishes()
    );

    orderEventPublisher.registerObserver(observer);
    orderEventPublisher.notifyObservers(order);
    verify(observer, times(1)).onOrderCreated(order);
  }

  public List<Dish> getDishes() {
    List<Dish> dishes = new ArrayList<>();
    dishes.add(new Dish(1L, "Dish 1", 10.0f));
    dishes.add(new Dish(2L, "Dish 2", 20.0f));
    dishes.add(new Dish(3L, "Dish 3", 30.0f));
    return dishes;
  }
}