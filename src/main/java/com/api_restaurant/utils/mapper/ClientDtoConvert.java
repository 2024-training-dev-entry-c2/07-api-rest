package com.api_restaurant.utils.mapper;

import com.api_restaurant.models.Client;
import com.api_restaurant.dto.client.ClientRequestDTO;
import com.api_restaurant.dto.client.ClientResponseDTO;

public class ClientDtoConvert {
    public static ClientResponseDTO convertToResponseDto(Client client) {
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
        clientResponseDTO.setId(client.getId());
        clientResponseDTO.setName(client.getName());
        clientResponseDTO.setLastName(client.getLastName());
        clientResponseDTO.setEmail(client.getEmail());
        clientResponseDTO.setFrequent(client.getFrequent());
        return clientResponseDTO;
    }

    public static Client convertToEntity(ClientRequestDTO dto) {
        Client client = new Client();
        client.setName(dto.getName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        return client;
    }
}
