package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.dto.ClientRequestDTO;
import com.restaurant.restaurant_management.dto.ClientResponseDTO;
import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientControllerTest {

  private final ClientService clientService;
  private final WebTestClient webTestClient;

  private Client client;
  private ClientRequestDTO clientRequestDTO;
  private ClientResponseDTO clientResponseDTO;
  private List<ClientResponseDTO> clientResponseDTOList;
  private List<Client> clientList;

  public ClientControllerTest() {
    clientService = mock(ClientService.class);
    webTestClient = WebTestClient.bindToController(new ClientController(clientService)).build();
  }


  @BeforeEach
  void setUp() {
    client = new Client(1L,"Juan","Perez","juanperez@gmail.com","123456789","Av. de la Independencia, 1",true);
    clientRequestDTO = new ClientRequestDTO("Juan", "Perez", "juanperez@gmail.com", "123456789", "Av. de la Independencia, 1", true);
    clientResponseDTO = new ClientResponseDTO(1L, "Juan", "Perez", "juanperez@gmail.com", "123456789", "Av. de la Independencia, 1", true);
    clientResponseDTOList = List.of(
      new ClientResponseDTO(1L, "Hugo", "McPato", "hugo@gmail.com", "313456789", "Av. de la Independencia, 1", true),
      new ClientResponseDTO(2L, "Paco", "McPato", "paco@gmail.com", "323456789", "Av. de la Independencia, 1", true),
      new ClientResponseDTO(3L, "Luis", "McPato", "luis@gmail.com", "333456789", "Av. de la Independencia, 1", true)
    );
    clientList = List.of(
      new Client(1L, "Hugo", "McPato", "hugo@gmail.com", "313456789", "Av. de la Independencia, 1", true),
      new Client(2L, "Paco", "McPato", "paco@gmail.com", "323456789", "Av. de la Independencia, 1", true),
      new Client(3L, "Luis", "McPato", "luis@gmail.com", "333456789", "Av. de la Independencia, 1", true)
    );
  }

  @Test
  @DisplayName("Save client")
  void saveClient() {
    when(clientService.saveClient(any(Client.class))).thenReturn(client);

    webTestClient.post()
      .uri("/api/client")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(clientRequestDTO)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(ClientResponseDTO.class)
      .value(client1->{
        assertEquals(clientResponseDTO.getId(), client1.getId());
        assertEquals(clientResponseDTO.getName(), client1.getName());
        assertEquals(clientResponseDTO.getLastName(), client1.getLastName());
        assertEquals(clientResponseDTO.getEmail(), client1.getEmail());
        assertEquals(clientResponseDTO.getPhone(), client1.getPhone());
        assertEquals(clientResponseDTO.getAddress(), client1.getAddress());
        assertEquals(clientResponseDTO.getIsFrequent(), client1.getIsFrequent());
      });

    Mockito.verify(clientService).saveClient(any(Client.class));
  }

  @Test
  @DisplayName("Get client")
  void getClient() {
    when(clientService.getClient(any(Long.class))).thenReturn(Optional.of(client));

    webTestClient.get()
      .uri("/api/client/{id}", 1)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(ClientResponseDTO.class)
      .value(client1->{
        assertEquals(clientResponseDTO.getId(), client1.getId());
        assertEquals(clientResponseDTO.getName(), client1.getName());
        assertEquals(clientResponseDTO.getLastName(), client1.getLastName());
        assertEquals(clientResponseDTO.getEmail(), client1.getEmail());
        assertEquals(clientResponseDTO.getPhone(), client1.getPhone());
        assertEquals(clientResponseDTO.getAddress(), client1.getAddress());
        assertEquals(clientResponseDTO.getIsFrequent(), client1.getIsFrequent());
      });

    Mockito.verify(clientService).getClient(any(Long.class));
  }

  @Test
  @DisplayName("List clients")
  void getClients() {
    when(clientService.listClients()).thenReturn(clientList);

    webTestClient.get()
      .uri("/api/client")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(ClientResponseDTO.class)
      .hasSize(3)
      .value(clients->{
        assertEquals(clientResponseDTOList.size(), clients.size());
        assertEquals("Hugo", clients.get(0).getName());
        assertEquals("Paco", clients.get(1).getName());
        assertEquals("Luis", clients.get(2).getName());
      });

    Mockito.verify(clientService).listClients();
  }

  @Test
  @DisplayName("Update client")
  void updateClient() {
    when(clientService.updateClient(any(Long.class), any(Client.class))).thenReturn(client);

    webTestClient.put()
      .uri("/api/client/{id}", 1)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(clientRequestDTO)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(ClientResponseDTO.class)
      .value(client1->{
        assertEquals(clientResponseDTO.getId(), client1.getId());
        assertEquals(clientResponseDTO.getName(), client1.getName());
        assertEquals(clientResponseDTO.getLastName(), client1.getLastName());
        assertEquals(clientResponseDTO.getEmail(), client1.getEmail());
        assertEquals(clientResponseDTO.getPhone(), client1.getPhone());
        assertEquals(clientResponseDTO.getAddress(), client1.getAddress());
        assertEquals(clientResponseDTO.getIsFrequent(), client1.getIsFrequent());
      });
    Mockito.verify(clientService).updateClient(any(Long.class), any(Client.class));
  }

  @Test
  @DisplayName("Update client - Client not found")
  void updateClient_NotFound() {
    when(clientService.updateClient(any(Long.class), any(Client.class)))
      .thenThrow(new RuntimeException("Client not found"));

    webTestClient.put()
      .uri("/api/client/{id}", 1)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(clientRequestDTO)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody().isEmpty();

    Mockito.verify(clientService).updateClient(any(Long.class), any(Client.class));
  }


  @Test
  @DisplayName("Delete client")
  void deleteClient() {
    doNothing().when(clientService).deleteClient(any(Long.class));

    webTestClient.delete()
      .uri("/api/client/{id}", 1)
      .exchange()
      .expectStatus().isNoContent()
      .expectBody().isEmpty();

    Mockito.verify(clientService).deleteClient(any(Long.class));
  }
}