package com.restaurant.management.utils;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.dto.ClientRequestDTO;
import com.restaurant.management.models.dto.ClientResponseDTO;

public class DtoClientConverter {
  public static Client toClient(ClientRequestDTO clientRequestDTO){
    Client client = new Client();
    client.setName(clientRequestDTO.getName());
    client.setEmail(clientRequestDTO.getEmail());
    return client;
  }

  public static ClientResponseDTO toClientResponseDTO(Client client){
    ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
    clientResponseDTO.setId(client.getId());
    clientResponseDTO.setName(client.getName());
    clientResponseDTO.setEmail(client.getEmail());
    clientResponseDTO.setFrequent(client.getFrequent());
    return clientResponseDTO;
  }

}
