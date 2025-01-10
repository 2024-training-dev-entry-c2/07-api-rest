package com.example.restaurant.services.handlers;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegularCustomerDiscountHandlerTest {

  @Mock
  private OrderRepository orderRepository;

  private RegularCustomerDiscountHandler regularCustomerDiscountHandler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    regularCustomerDiscountHandler = new RegularCustomerDiscountHandler(orderRepository);
  }

  @Test
  @DisplayName("Calcular precio con descuento para cliente regular")
  void calculatePriceWithDiscount() {
    Customer customer = new Customer(
            1L,
            "John ",
            "Doe",
            "emailJohn@hotmail.com",
            "+1 234"
    );

    Order order = new Order();
    order.setCustomer(customer);
    Dish dish = new Dish();
    Float initialPrice = 100.0f;

    when(orderRepository.countByCustomerId(customer.getCustomerId())).thenReturn(10L);

    Float result = regularCustomerDiscountHandler.calculatePrice(initialPrice, order, dish);

    assertEquals(97.62f, result, 0.01);
    verify(orderRepository, times(1)).countByCustomerId(customer.getCustomerId());
  }

  @Test
  @DisplayName("Calcular precio sin descuento para cliente no regular")
  void calculatePriceWithoutDiscount() {
    Customer customer = new Customer();
    customer.setCustomerId(1L);
    Order order = new Order();
    order.setCustomer(customer);
    Dish dish = new Dish();
    Float initialPrice = 100.0f;

    when(orderRepository.countByCustomerId(customer.getCustomerId())).thenReturn(5L);

    Float result = regularCustomerDiscountHandler.calculatePrice(initialPrice, order, dish);

    assertEquals(100.0f, result);
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