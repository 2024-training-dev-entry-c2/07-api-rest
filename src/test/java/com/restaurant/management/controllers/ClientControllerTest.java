package com.restaurant.management.controllers;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.dto.ClientRequestDTO;
import com.restaurant.management.models.dto.ClientResponseDTO;
import com.restaurant.management.services.ClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientControllerTest {
  private final WebTestClient webTestClient;
  private final ClientService clientService;

  ClientControllerTest() {
    this.clientService = mock(ClientService.class);
    this.webTestClient = WebTestClient.bindToController(new ClientController(clientService)).build();
  }

  @Test
  @DisplayName("Crear cliente")
  void addClient() {
    ClientRequestDTO clientRequest = new ClientRequestDTO("name", "email");
    Client client = new Client(1L, "name", "email");
    when(clientService.addClient(any(Client.class))).thenReturn(client);

    webTestClient.post()
      .uri("/api/clientes")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(clientRequest)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(ClientResponseDTO.class)
      .value(clientResponseDTO -> {
        assertEquals(clientResponseDTO.getId(), client.getId());
        assertEquals(clientResponseDTO.getName(), client.getName());
        assertEquals(clientResponseDTO.getEmail(), client.getEmail());
        assertEquals(clientResponseDTO.getFrequent(), client.getFrequent());
      });

      verify(clientService).addClient(any(Client.class));
  }

  @Test
  @DisplayName("Obtener cliente por id")
  void getClient() {
    Client client = new Client(1L, "name", "email");
    when(clientService.getClientById(any(Long.class))).thenReturn(Optional.of(client));

    webTestClient.get()
      .uri("/api/clientes/{id}",1L)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(ClientResponseDTO.class)
      .value(clientResponseDTO -> {
          assertEquals(clientResponseDTO.getId(), client.getId());
          assertEquals(clientResponseDTO.getName(), client.getName());
          assertEquals(clientResponseDTO.getEmail(), client.getEmail());
          assertEquals(clientResponseDTO.getFrequent(), client.getFrequent());
      });

    verify(clientService).getClientById(any(Long.class));
  }

  @Test
  @DisplayName("Obtener lista de clientes")
  void getClients() {
    when(clientService.getClients()).thenReturn(getClientList());

    webTestClient.get()
      .uri("/api/clientes")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(ClientResponseDTO.class)
      .hasSize(3)
      .value(clientResponseDTOs -> {
        assertEquals(clientResponseDTOs.get(0).getName(), getClientList().get(0).getName());
        assertEquals(clientResponseDTOs.get(1).getName(), getClientList().get(1).getName());
        assertEquals(clientResponseDTOs.get(2).getName(), getClientList().get(2).getName());
      });

    verify(clientService).getClients();
  }

  @Test
  @DisplayName("Actualizar cliente exitoso")
  void updateClient() {
    ClientRequestDTO clientRequest = new ClientRequestDTO("name", "email");
    Client client = new Client(1L, "name", "email");
    when(clientService.updateClient(any(Long.class), any(Client.class))).thenReturn(client);

    webTestClient.put()
      .uri("/api/clientes/{id}", 1L)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(clientRequest)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(ClientResponseDTO.class)
      .value(clientResponseDTO -> {
        assertEquals(clientResponseDTO.getId(), client.getId());
        assertEquals(clientResponseDTO.getName(), client.getName());
        assertEquals(clientResponseDTO.getEmail(), client.getEmail());
        assertEquals(clientResponseDTO.getFrequent(), client.getFrequent());
      });

    verify(clientService).updateClient(any(Long.class), any(Client.class));
  }

  @Test
  @DisplayName("Actualizar cliente con error")
  void updateClientError() {
    ClientRequestDTO clientRequest = new ClientRequestDTO("name", "email");
    Client client = new Client(1L, "name", "email");
    when(clientService.getClientById(any(Long.class))).thenReturn(Optional.of(client));
    when(clientService.updateClient(any(Long.class), any(Client.class))).thenThrow(new RuntimeException());

    webTestClient.put()
      .uri("/api/clientes/{id}", 1L)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(clientRequest)
      .exchange()
      .expectStatus().is4xxClientError();

    verify(clientService).updateClient(any(Long.class), any(Client.class));
  }

  @Test
  @DisplayName("Eliminar cliente")
  void deleteClient() {
    doNothing().when(clientService).deleteClient(any(Long.class));

    webTestClient.delete()
      .uri("/api/clientes/{id}", 1L)
      .exchange()
      .expectStatus().isNoContent();

    verify(clientService).deleteClient(any(Long.class));
  }

  private List<Client> getClientList() {
    return List.of(new Client(1L, "A", "Ve"),
      new Client(2L, "B", "Ce"),
      new Client(3L, "C", "De"));
  }
}