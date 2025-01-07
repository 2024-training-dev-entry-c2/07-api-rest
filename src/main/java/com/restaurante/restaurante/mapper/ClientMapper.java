package com.restaurante.restaurante.mapper;

import com.restaurante.restaurante.dto.ClientDTO;
import com.restaurante.restaurante.dto.OrderDTO;
import com.restaurante.restaurante.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {
    private final DishMapper dishMapper;
    private final OrderMapper orderMapper;

    @Autowired
    public ClientMapper(DishMapper dishMapper, OrderMapper orderMapper) {
        this.dishMapper = dishMapper;
        this.orderMapper = orderMapper;
    }

    public ClientDTO toDTO(Client client) {
        if (client == null) {
            return null;
        }

        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setUserType(client.getUserType());
        dto.setOrders(client.getOrders() != null
                ? client.getOrders().stream().map(orderMapper::toDTO).collect(Collectors.toList())
                : new ArrayList<>());
        dto.setTotalOrders(client.getTotalOrders());

        return dto;
    }

    public Client toEntity(ClientDTO dto) {
        if (dto == null) {
            return null;
        }

        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setUserType(dto.getUserType());
        client.setOrders(dto.getOrders() != null
                ? dto.getOrders().stream().map(orderMapper::toEntity).collect(Collectors.toList())
                : new ArrayList<>());

        return client;
    }

//    public List<ClientDTO> toDTOList(List<Client> clients) {
//        if (clients == null) {
//            return Collections.emptyList();
//        }
//        return clients.stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    public List<Client> toEntityList(List<ClientDTO> dtos) {
//        if (dtos == null) {
//            return Collections.emptyList();
//        }
//        return dtos.stream()
//                .map(this::toEntity)
//                .collect(Collectors.toList());
//    }
}