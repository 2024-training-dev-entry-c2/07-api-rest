package com.example.demo.DTO.converterDTO;

import com.example.demo.DTO.order.OrderRequestDTO;
import com.example.demo.DTO.order.OrderResponseDTO;
import com.example.demo.models.Client;
import com.example.demo.models.Dishfood;
import com.example.demo.models.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {
    public static Order toEntity(OrderRequestDTO dto, Client client, List<Dishfood> dishfoods, Double total) {
        return Order.builder()
                .client(client)
                .localDate(dto.getLocalDate())
                .dishfoods(dishfoods)
                .total(total)
                .build();
    }

    public static OrderResponseDTO toResponseDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .client(ClientConverter.toResponseDTO(order.getClient()))
                .localDate(order.getLocalDate())
                .total(order.getTotal() < 0 ? 0.0 : order.getTotal())
                .dishfoodIds(order.getDishfoods().stream().map(Dishfood::getId).collect(Collectors.toList())) // Solo IDs
                .build();
    }
}
