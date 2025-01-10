package com.example.restaurant.services.order.commands;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderCommand {

  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;
  private final DishRepository dishRepository;
  private final RegularCustomerDiscountHandler regularCustomerDiscountHandler;
  private final OrderMapper orderMapper;

  public OrderResponseDTO execute(OrderRequestDTO orderRequestDTO) {
    Order order = orderMapper.toEntity(orderRequestDTO);
    Customer customer = customerRepository.findById(orderRequestDTO.getCustomerId())
            .orElseThrow(() -> new IllegalArgumentException("No se encontró cliente con ID: " + orderRequestDTO.getCustomerId()));
    order.setCustomer(customer);
    List<Dish> dishes = dishRepository.findAllById(orderRequestDTO.getDishIds());
    order.setDishes(dishes);
    order.setTotalOrderPrice(calculateTotal(order));
    orderRepository.save(order);
    return orderMapper.toDTO(order);
  }

  private Float calculateTotal(Order order) {
    Float total = 0f;

    for (Dish dish : order.getDishes()) {
      Float dishPrice = regularCustomerDiscountHandler.calculatePrice(dish.getPrice(), order, dish);
      total += dishPrice;
    }

    return total;
  }


}
