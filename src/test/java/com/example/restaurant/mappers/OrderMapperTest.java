package com.example.restaurant.mappers;

import com.example.restaurant.models.Customer;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.models.dto.order.OrderRequestDTO;
import com.example.restaurant.models.dto.order.OrderResponseDTO;
import com.example.restaurant.repositories.DishRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderMapperTest {

  private final DishRepository dishRepository = mock(DishRepository.class);
  private final OrderMapper orderMapper = new OrderMapper(new CustomerMapper(), new DishMapper(), dishRepository);

  @Test
  @DisplayName("Convertir de RequestDTO a entidad")
  void toEntity() {
    Dish dish = new Dish();
    dish.setDishId(1L);
    dish.setName("Dish 1");
    dish.setPrice(10.0f);

    OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
    orderRequestDTO.setCustomerId(1L);
    orderRequestDTO.setDishIds(List.of(1L));
    orderRequestDTO.setDate(new Date());

    Customer customer = new Customer();
    customer.setCustomerId(1L);

    when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));

    Order order = orderMapper.toEntity(orderRequestDTO);
    order.setCustomer(customer);

    assertEquals(1L, order.getDishes().get(0).getDishId());
    assertEquals("Dish 1", order.getDishes().get(0).getName());
    assertEquals(10.0f, order.getDishes().get(0).getPrice());
  }

  @Test
  @DisplayName("Convertir de Entidad a ResponseDTO")
  void toDTO() {
    Dish dish = new Dish();
    dish.setName("Dish 1");
    dish.setPrice(10.0f);

    Customer customer = new Customer();
    customer.setCustomerId(1L);
    customer.setOrders(Collections.emptyList());

    CustomerResponseDTO customerDTO = new CustomerResponseDTO();
    customerDTO.setCustomerId(1L);

    Order order = new Order();
    order.setCustomer(customer);
    order.setDishes(List.of(dish));
    order.setDate(new Date());

    OrderResponseDTO responseDTO = orderMapper.toDTO(order);
    responseDTO.setCustomer(customerDTO);

    assertEquals(1L, responseDTO.getCustomer().getCustomerId());
    assertEquals("Dish 1", responseDTO.getDishes().get(0).getName());
    assertEquals(10.0f, responseDTO.getDishes().get(0).getPrice());
  }
}