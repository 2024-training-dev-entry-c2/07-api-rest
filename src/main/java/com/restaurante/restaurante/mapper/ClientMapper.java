package com.restaurante.restaurante.mapper;

import com.restaurante.restaurante.dto.ClientDTO;
import com.restaurante.restaurante.models.Client;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        if (client == null) {
            return null;
        }

        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setUserType(client.getUserType());
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
        return client;
    }

    public List<ClientDTO> toDTOList(List<Client> clients) {
        if (clients == null) {
            return Collections.emptyList();
        }
        return clients.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}