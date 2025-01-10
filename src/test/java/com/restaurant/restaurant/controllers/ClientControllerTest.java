package com.restaurant.restaurant.controllers;

import com.restaurant.restaurant.dtos.ClientDTO;
import com.restaurant.restaurant.enums.ClientType;
import com.restaurant.restaurant.services.ClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientControllerTest {

  private final WebTestClient webTestClient;
  private final ClientService clientService;

  public ClientControllerTest(){
    clientService = mock(ClientService.class);
    webTestClient = WebTestClient.bindToController(new ClientController(clientService)).build();
  }

  @Test
  @DisplayName("Get all clients")
  void getAllClients() {
    ClientDTO clientDTO1 = new ClientDTO(5L, "RM", "Jara", "rm@gmail.com", "1230984567", ClientType.COMUN);
    ClientDTO clientDTO2 = new ClientDTO(6L, "Ana", "Perez", "ana@gmail.com", "9876543210", ClientType.FRECUENT);

    when(clientService.findAll()).thenReturn(List.of(clientDTO1, clientDTO2));

    webTestClient
            .get()
            .uri("/api/clients")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data[0].name").isEqualTo(clientDTO1.getName())
            .jsonPath("$.data[1].name").isEqualTo(clientDTO2.getName());
  }

  @Test
  @DisplayName("Get client by ID")
  void getClientById() {
    ClientDTO clientDTO = new ClientDTO(5L, "RM", "Jara", "rm@gmail.com", "1230984567", ClientType.COMUN);
    when(clientService.findById(5L)).thenReturn(clientDTO);

    webTestClient
            .get()
            .uri("/api/clients/5")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.id").isEqualTo(clientDTO.getId().intValue())
            .jsonPath("$.data.name").isEqualTo(clientDTO.getName());
  }


  @Test
  @DisplayName("Create client")
  void createClient() {
    ClientDTO clientDTO = new ClientDTO(5L, "RM", "Jara", "rm@gmail.com", "1230984567", ClientType.COMUN);
    when(clientService.createClient(any(ClientDTO.class))).thenReturn(clientDTO);
    webTestClient
            .post()
            .uri("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(clientDTO)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.name").isEqualTo(clientDTO.getName())
            .jsonPath("$.data.lastName").isEqualTo(clientDTO.getLastName())
            .jsonPath("$.data.email").isEqualTo(clientDTO.getEmail())
            .jsonPath("$.data.phone").isEqualTo(clientDTO.getPhone())
            .jsonPath("$.data.type").isEqualTo(clientDTO.getType().toString());
  }

  @Test
  @DisplayName("Update client")
  void updateClient() {
    ClientDTO clientDTO = new ClientDTO(5L, "RM", "Jara", "rm@gmail.com", "1230984567", ClientType.COMUN);
    ClientDTO updatedClientDTO = new ClientDTO(5L, "RM", "Jaramillo", "rm@gmail.com", "1230984567", ClientType.COMUN);

    when(clientService.updateClient(eq(5L), any(ClientDTO.class))).thenReturn(updatedClientDTO);

    webTestClient
            .put()
            .uri("/api/clients/5")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(updatedClientDTO)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.id").isEqualTo(updatedClientDTO.getId().intValue())
            .jsonPath("$.data.email").isEqualTo(updatedClientDTO.getEmail());
  }

  @Test
  @DisplayName("Delete client")
  void deleteClient() {
    doNothing().when(clientService).deleteClient(5L);

    webTestClient
            .delete()
            .uri("/api/clients/5")
            .exchange()
            .expectStatus().isNoContent();
  }
}