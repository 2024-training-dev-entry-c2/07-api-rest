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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetOrderByIdCommandTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private OrderMapper orderMapper;

  private GetOrderByIdCommand getOrderByIdCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    getOrderByIdCommand = new GetOrderByIdCommand(orderRepository, orderMapper);
  }

  @Test
  @DisplayName("Obtener orden por ID existente")
  void execute_existingOrder() {

    Customer customer = new Customer(
            1L,
            "John",
            "Doe",
            "johnDoe@gmail.com",
            "+123"
    );

    Date date = new Date();

    Order order = new Order(
            1L,
            date,
            customer,
            getDishes()
    );
    Long orderId = 1L;
    OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    when(orderMapper.toDTO(order)).thenReturn(orderResponseDTO);

    OrderResponseDTO result = getOrderByIdCommand.execute(orderId);

    assertNotNull(result, "The result should not be null");
    assertEquals(orderResponseDTO, result, "The result should match the expected OrderResponseDTO");

    verify(orderRepository, times(1)).findById(orderId);
    verify(orderMapper, times(1)).toDTO(order);
  }

  @Test
  @DisplayName("Lanzar excepción al no encontrar orden por ID")
  void execute_nonExistingOrder() {
    Long orderId = 1L;

    when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> getOrderByIdCommand.execute(orderId));
    assertEquals("No se encontró la orden con ID: " + orderId, exception.getMessage());

    verify(orderRepository, times(1)).findById(orderId);
    verify(orderMapper, never()).toDTO(any(Order.class));
  }

  public List<Dish> getDishes() {
    List<Dish> dishes = new ArrayList<>();
    dishes.add(new Dish(1L, "Dish 1", 10.0f));
    dishes.add(new Dish(2L, "Dish 2", 20.0f));
    dishes.add(new Dish(3L, "Dish 3", 30.0f));
    return dishes;
  }
}