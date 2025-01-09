package com.api_restaurant.utils.mapper;

import com.api_restaurant.dto.client.ClientRequestDTO;
import com.api_restaurant.dto.client.ClientResponseDTO;
import com.api_restaurant.models.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientDtoConvertTest {

    @Test
    @DisplayName("Convert to Response DTO - Success")
    void convertToResponseDto() {
        Client client = new Client();
        client.setId(1L);
        client.setName("John");
        client.setLastName("Doe");
        client.setEmail("john.doe@example.com");
        client.setFrequent(true);

        ClientResponseDTO responseDTO = ClientDtoConvert.convertToResponseDto(client);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("John", responseDTO.getName());
        assertEquals("Doe", responseDTO.getLastName());
        assertEquals("john.doe@example.com", responseDTO.getEmail());
        assertTrue(responseDTO.getFrequent());
    }

    @Test
    @DisplayName("Convert to Entity - Success")
    void convertToEntity() {
        ClientRequestDTO requestDTO = new ClientRequestDTO();
        requestDTO.setName("John");
        requestDTO.setLastName("Doe");
        requestDTO.setEmail("john.doe@example.com");

        Client client = ClientDtoConvert.convertToEntity(requestDTO);

        assertNotNull(client);
        assertEquals("John", client.getName());
        assertEquals("Doe", client.getLastName());
        assertEquals("john.doe@example.com", client.getEmail());
    }
}