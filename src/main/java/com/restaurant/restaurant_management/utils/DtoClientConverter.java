package com.restaurant.restaurant_management.utils;

import com.restaurant.restaurant_management.dto.ClientRequestDTO;
import com.restaurant.restaurant_management.dto.ClientResponseDTO;
import com.restaurant.restaurant_management.models.Client;

public class DtoClientConverter {

  public static Client convertToClient(ClientRequestDTO clientRequestDTO) {
    Client client = new Client();
    client.setName(clientRequestDTO.getName());
    client.setLastName(clientRequestDTO.getLastName());
    client.setEmail(clientRequestDTO.getEmail());
    client.setPhone(clientRequestDTO.getPhone());
    client.setAddress(clientRequestDTO.getAddress());
    client.setIsFrequent(clientRequestDTO.getIsFrequent());
    return client;
  }

  public static ClientResponseDTO convertToResponseDTO(Client client) {
    ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
    clientResponseDTO.setId(client.getId());
    clientResponseDTO.setName(client.getName());
    clientResponseDTO.setLastName(client.getLastName());
    clientResponseDTO.setEmail(client.getEmail());
    clientResponseDTO.setPhone(client.getPhone());
    clientResponseDTO.setAddress(client.getAddress());
    clientResponseDTO.setIsFrequent(client.getIsFrequent());
    return clientResponseDTO;
  }
}
