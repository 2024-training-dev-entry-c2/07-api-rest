package com.example.demo.DTO.converterDTO;

import com.example.demo.DTO.ClientRequestDTO;
import com.example.demo.DTO.ClientResponseDTO;
import com.example.demo.models.Client;
import com.example.demo.models.Order;

import java.util.Collections;
import java.util.stream.Collectors;

public class ClientConverter {


    public static ClientResponseDTO toResponseDTO(Client client) {
        if (client == null) {
            return null;
        }
        return ClientResponseDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .isOften(client.getIsOften())
                .orderIds(client.getOrderList() == null ? Collections.emptyList() : client.getOrderList()
                        .stream().map(Order::getId).collect(Collectors.toList())) // Solo IDs
                .build();
    }


    public static Client toEntity(ClientRequestDTO clientRequestDTO) {
        if (clientRequestDTO == null) {
            return null;
        }
        return Client.builder()
                .name(clientRequestDTO.getName())
                .email(clientRequestDTO.getEmail())
                .build();
    }

}
