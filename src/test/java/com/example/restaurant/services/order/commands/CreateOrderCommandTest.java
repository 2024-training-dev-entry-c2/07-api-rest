package com.example.restaurant.services.order.commands;

import com.example.restaurant.mappers.OrderMapper;
import com.example.restaurant.models.Customer;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.dto.order.OrderRequestDTO;
import com.example.restaurant.models.dto.order.OrderResponseDTO;
import com.example.restaurant.repositories.CustomerRepository;
import com.example.restaurant.repositories.DishRepository;
import com.example.restaurant.repositories.OrderRepository;
import com.example.restaurant.services.handlers.RegularCustomerDiscountHandler;
import com.example.restaurant.services.handlers.PopularDishMarkupHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateOrderCommandTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private RegularCustomerDiscountHandler regularCustomerDiscountHandler;

    @Mock
    private PopularDishMarkupHandler popularDishMarkupHandler;

    @InjectMocks
    private CreateOrderCommand createOrderCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Crear una orden")
    void execute() {

        Customer customer = new Customer(
                1L,
                "John",
                "Doe",
                "johnDoe@gmail.com",
                "+123"
        );

        Date date = new Date();

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setDate(date);
        orderRequestDTO.setCustomerId(customer.getId());
        orderRequestDTO.setDishIds(getDishes().stream().map(Dish::getId).toList());

        Order order = new Order();
        order.setDate(date);
        order.setCustomer(customer);
        order.setDishes(getDishes());

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setDate(date);
        savedOrder.setCustomer(customer);
        savedOrder.setDishes(getDishes());

        when(dishRepository.findAllById(orderRequestDTO.getDishIds())).thenReturn(getDishes());
        when(customerRepository.findById(customer.getId())).thenReturn(java.util.Optional.of(customer));
        when(orderMapper.toEntity(orderRequestDTO)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(savedOrder);
        when(regularCustomerDiscountHandler.calculatePrice(anyFloat(), any(Order.class), any(Dish.class))).thenReturn(10.0f);
        when(popularDishMarkupHandler.calculatePrice(anyFloat(), any(Order.class), any(Dish.class))).thenReturn(5.0f);

        OrderResponseDTO result = createOrderCommand.execute(orderRequestDTO);

        assertEquals(savedOrder.getId(), result.getId());
        assertEquals(savedOrder.getDate(), result.getDate());

        verify(orderMapper).toEntity(orderRequestDTO);
        verify(orderRepository).save(order);
        verify(popularDishMarkupHandler).calculatePrice(anyFloat(), any(Order.class), any(Dish.class));
    }

    public List<Dish> getDishes() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish(1L, "Dish 1", 10.0f));
        dishes.add(new Dish(2L, "Dish 2", 20.0f));
        dishes.add(new Dish(3L, "Dish 3", 30.0f));
        return dishes;
    }
}