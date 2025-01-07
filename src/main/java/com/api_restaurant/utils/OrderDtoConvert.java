package com.api_restaurant.utils;

import com.api_restaurant.dto.order.OrderRequestDTO;
import com.api_restaurant.dto.order.OrderResponseDTO;
import com.api_restaurant.models.Client;
import com.api_restaurant.models.Dish;
import com.api_restaurant.models.Order;
import com.api_restaurant.repositories.ClientRepository;
import com.api_restaurant.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDtoConvert {

    private final ClientRepository clientRepository;
    private final DishRepository dishRepository;
    private final DishDtoConvert dishDtoConvert;

    @Autowired
    public OrderDtoConvert(ClientRepository clientRepository, DishRepository dishRepository, DishDtoConvert dishDtoConvert) {
        this.clientRepository = clientRepository;
        this.dishRepository = dishRepository;
        this.dishDtoConvert = dishDtoConvert;
    }

    public OrderResponseDTO convertToResponseDto(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        if (order.getClient() != null) {
            orderResponseDTO.setClientId(order.getClient().getId());
        }
        orderResponseDTO.setDishes(order.getDishes().stream()
                .map(dishDtoConvert::convertToResponseDto)
                .collect(Collectors.toList()));
        orderResponseDTO.setTotal(order.getTotal());
        return orderResponseDTO;
    }

    public Order convertToEntity(OrderRequestDTO dto) {
        Client client = clientRepository.findById(dto.getClientId()).orElse(null);
        List<Dish> dishes = dto.getDishIds().stream()
                .map(dishRepository::findById)
                .map(optionalDish -> optionalDish.orElse(null))
                .collect(Collectors.toList());
        return new Order(client, dishes, null);
    }
}