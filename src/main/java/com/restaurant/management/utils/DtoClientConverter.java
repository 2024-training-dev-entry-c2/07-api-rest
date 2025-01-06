package com.restaurant.management.utils;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.dto.ClientRequestDTO;
import com.restaurant.management.models.dto.ClientResponseDTO;

public class DtoClientConverter {
  public static Client toClient(ClientRequestDTO clientRequestDTO){
    return new Client(
      clientRequestDTO.getName(),
      clientRequestDTO.getEmail()
    );
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
