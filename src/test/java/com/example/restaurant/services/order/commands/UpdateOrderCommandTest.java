package com.example.restaurant.services.order.commands;

import com.example.restaurant.mappers.CustomerMapper;
import com.example.restaurant.mappers.DishMapper;
import com.example.restaurant.models.Customer;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.dto.order.OrderRequestDTO;
import com.example.restaurant.models.dto.order.OrderResponseDTO;
import com.example.restaurant.repositories.CustomerRepository;
import com.example.restaurant.repositories.DishRepository;
import com.example.restaurant.repositories.OrderRepository;
import com.example.restaurant.mappers.OrderMapper;
import com.example.restaurant.services.handlers.RegularCustomerDiscountHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateOrderCommandTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private DishRepository dishRepository;

  @Mock
  private RegularCustomerDiscountHandler regularCustomerDiscountHandler;

  @Mock
  private OrderMapper orderMapper;

  @Mock
  private CustomerMapper customerMapper;

  @Mock
  private DishMapper dishMapper;

  private UpdateOrderCommand updateOrderCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    updateOrderCommand = new UpdateOrderCommand(orderRepository, customerRepository, dishRepository, regularCustomerDiscountHandler, orderMapper);
  }

  @Test
  @DisplayName("Actualizar orden existente")
  void execute_existingOrder() {
    Long orderId = 1L;

    Customer customer = new Customer(
            1L,
            "John",
            "Doe",
            "johnDoe@gmail.com",
            "+123"
    );

    Customer customerUpdated = new Customer(
            1L,
            "Johnnas",
            "Doe",
            "johnahDoe@gmail.com",
            "+1234"
    );

    Date date = new Date();

    OrderRequestDTO orderRequestDTO = new OrderRequestDTO(
            date,
            customerUpdated.getCustomerId(),
            getDishes().stream().map(Dish::getDishId).toList()
    );

    Order existingOrder = new Order(
            orderId,
            date,
            customer,
            getDishes()
    );

    Order updatedOrder = new Order(
            orderId,
            date,
            customerUpdated,
            getDishes()
    );

    Order savedOrder = new Order(
            orderId,
            date,
            customerUpdated,
            getDishes()
    );

    OrderResponseDTO orderResponseDTO = new OrderResponseDTO(
            orderId,
            date,
            customerMapper.toDTO(customerUpdated),
            getDishes().stream().map(dish -> dishMapper.toDTO(dish)).toList()
    );

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
    when(orderMapper.toEntity(orderRequestDTO)).thenReturn(updatedOrder);
    when(orderRepository.save(updatedOrder)).thenReturn(savedOrder);
    when(orderMapper.toDTO(savedOrder)).thenReturn(orderResponseDTO);

    OrderResponseDTO result = updateOrderCommand.execute(orderId, orderRequestDTO);

    assertNotNull(result, "The result should not be null");
    assertEquals(orderResponseDTO, result, "The result should match the expected OrderResponseDTO");

    verify(orderRepository).findById(orderId);
    verify(orderMapper).toEntity(orderRequestDTO);
    verify(orderRepository).save(updatedOrder);
    verify(orderMapper).toDTO(savedOrder);
  }

  @Test
  @DisplayName("Lanzar excepción al no encontrar orden por ID")
  void execute_nonExistingOrder() {
    Long orderId = 1L;
    OrderRequestDTO orderRequestDTO = new OrderRequestDTO();

    when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> updateOrderCommand.execute(orderId, orderRequestDTO));
    assertEquals("No se encontró la orden con ID: " + orderId, exception.getMessage());

    verify(orderRepository, times(1)).findById(orderId);
    verify(orderMapper, never()).toEntity(any(OrderRequestDTO.class));
    verify(orderRepository, never()).save(any(Order.class));
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