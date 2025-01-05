package com.example.demo.DTO.converterDTO;

import com.example.demo.DTO.DishfoodRequestDTO;
import com.example.demo.DTO.DishfoodResponseDTO;
import com.example.demo.DTO.OrderResponseDTO;
import com.example.demo.models.Dishfood;
import com.example.demo.models.Menu;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DishfoodConverter {
    public static Dishfood toEntity(DishfoodRequestDTO dto, Menu menu) {
        return Dishfood.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .isPopular(dto.getIsPopular())
                .menu(menu)
                .build();
    }

    public static DishfoodResponseDTO toResponseDTO(Dishfood dishfood) {
        return DishfoodResponseDTO.builder()
                .id(dishfood.getId())
                .name(dishfood.getName())
                .price(dishfood.getPrice())
                .isPopular(dishfood.getIsPopular())
                .menu(dishfood.getMenu().getName())
                .orderList(dishfood.getOrderList().stream()
                        .map(order -> OrderResponseDTO.builder()
                                .id(order.getId())
                                .client(ClientConverter.toResponseDTO(order.getClient()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
