package com.example.restaurant.services.order.commands;

import com.example.restaurant.models.Customer;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.dto.order.OrderResponseDTO;
import com.example.restaurant.repositories.OrderRepository;
import com.example.restaurant.mappers.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListOrdersCommandTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private OrderMapper orderMapper;

  private ListOrdersCommand listOrdersCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    listOrdersCommand = new ListOrdersCommand(orderRepository, orderMapper);
  }

  @Test
  @DisplayName("Listar todas las órdenes")
  void execute() {

    Customer customer1 = new Customer(
            1L,
            "John",
            "Doe",
            "johnDoe@gmail.com",
            "+123"
    );

    Customer customer2 = new Customer(
            2L,
            "Jane",
            "Doe",
            "janeDoe@gmail.com",
            "+456"
    );

    Date date = new Date();

    Order order1 = new Order(
            1L,
            date,
            customer1,
            getDishes1()
    );

    Order order2 = new Order(
            2L,
            date,
            customer2,
            getDishes2()
    );

    OrderResponseDTO orderResponseDTO1 = new OrderResponseDTO();
    OrderResponseDTO orderResponseDTO2 = new OrderResponseDTO();

    when(orderRepository.findAll()).thenReturn(List.of(order1, order2));
    when(orderMapper.toDTO(order1)).thenReturn(orderResponseDTO1);
    when(orderMapper.toDTO(order2)).thenReturn(orderResponseDTO2);

    List<OrderResponseDTO> result = listOrdersCommand.execute();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(orderResponseDTO1, result.get(0));
    assertEquals(orderResponseDTO2, result.get(1));

    verify(orderRepository, times(1)).findAll();
  }

  public List<Dish> getDishes1() {
    List<Dish> dishes = new ArrayList<>();
    dishes.add(new Dish(1L, "Dish 1", 10.0f));
    dishes.add(new Dish(2L, "Dish 2", 20.0f));
    dishes.add(new Dish(3L, "Dish 3", 30.0f));
    return dishes;
  }

  public List<Dish> getDishes2() {
    List<Dish> dishes = new ArrayList<>();
    dishes.add(new Dish(6L, "Dish 4", 13.0f));
    dishes.add(new Dish(7L, "Dish 5", 21.0f));
    dishes.add(new Dish(9L, "Dish 6", 35.0f));
    return dishes;
  }
}