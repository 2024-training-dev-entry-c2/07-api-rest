package com.example.restaurant.mappers;

import com.example.restaurant.models.Customer;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.dto.order.OrderRequestDTO;
import com.example.restaurant.models.dto.order.OrderResponseDTO;
import com.example.restaurant.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

  private final CustomerMapper customerMapper;
  private final DishMapper dishMapper;
  private final DishRepository dishRepository;

  public Order toEntity(OrderRequestDTO dto) {
    Order order = new Order();
    order.setDate(dto.getDate());
    Customer customer = new Customer();
    customer.setCustomerId(dto.getCustomerId());
    order.setCustomer(customer);
    List<Dish> dishes = dto.getDishIds().stream()
            .map(dishId -> dishRepository.findById(dishId)
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró plato con ID: " + dishId)))
            .collect(Collectors.toList());
    order.setDishes(dishes);
    return order;
  }

  public OrderResponseDTO toDTO(Order order) {
    OrderResponseDTO dto = new OrderResponseDTO();
    dto.setOrderId(order.getOrderId());
    dto.setTotalOrderPrice(order.getTotalOrderPrice());
    dto.setDate(order.getDate());
    dto.setCustomer(customerMapper.toDTO(order.getCustomer()));
    dto.setDishes(order.getDishes().stream()
            .map(dishMapper::toDTO)
            .toList());
    return dto;
  }

}
