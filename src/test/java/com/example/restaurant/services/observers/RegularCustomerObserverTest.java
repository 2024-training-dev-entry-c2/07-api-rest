package com.example.restaurant.services.observers;

import com.example.restaurant.models.Customer;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class RegularCustomerObserverTest {

  @Mock
  private OrderRepository orderRepository;

  private RegularCustomerObserver regularCustomerObserver;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    regularCustomerObserver = new RegularCustomerObserver(orderRepository);
  }

  @Test
  @DisplayName("Notificar observador de cliente regular")
  void onOrderCreated() {
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

    when(orderRepository.countByCustomerId(customer.getCustomerId())).thenReturn(10L);

    regularCustomerObserver.onOrderCreated(order);

    verify(orderRepository, times(1)).countByCustomerId(customer.getCustomerId());

  }

  public List<Dish> getDishes() {
    List<Dish> dishes = new ArrayList<>();
    dishes.add(new Dish(1L, "Dish 1", 10.0f));
    dishes.add(new Dish(2L, "Dish 2", 20.0f));
    dishes.add(new Dish(3L, "Dish 3", 30.0f));
    return dishes;
  }
}