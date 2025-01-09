package com.api_restaurant.controllers;

import com.api_restaurant.dto.client.ClientRequestDTO;
import com.api_restaurant.dto.client.ClientResponseDTO;
import com.api_restaurant.models.Client;
import com.api_restaurant.services.ClientService;
import com.api_restaurant.utils.mapper.ClientDtoConvert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    @DisplayName("Crear Cliente")
    void addClient() {
        Client client = new Client(1L, "Juan", "Perez", "j@p.com", false);

        when(clientService.addClient(any(Client.class))).thenReturn(client);

        webTestClient.post()
                .uri("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(client)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Client.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response.getId(), client.getId());
                    assertEquals(response.getName(), client.getName());
                    assertEquals(response.getLastName(), client.getLastName());
                    assertEquals(response.getEmail(), client.getEmail());
                    assertEquals(response.getFrequent(), client.getFrequent());
                });
        Mockito.verify(clientService).addClient(any(Client.class));
    }

    @Test
    @DisplayName("Obtener Cliente por ID")
    void getClient() {
        Client client = new Client(1L, "Juan", "Perez", "j@p.com", false);

        when(clientService.getClient(1L)).thenReturn(Optional.of(client));

        webTestClient.get()
                .uri("/client/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Client.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response.getId(), client.getId());
                    assertEquals(response.getName(), client.getName());
                    assertEquals(response.getLastName(), client.getLastName());
                    assertEquals(response.getEmail(), client.getEmail());
                    assertEquals(response.getFrequent(), client.getFrequent());
                });
        Mockito.verify(clientService).getClient(1L);
    }

    @Test
    @DisplayName("Obtener Cliente por ID - No Encontrado")
    void getClientNotFound() {
        when(clientService.getClient(1L)).thenReturn(Optional.empty());

        webTestClient.get()
                .uri("/client/1")
                .exchange()
                .expectStatus().isNotFound();
        Mockito.verify(clientService).getClient(1L);
    }

    @Test
    @DisplayName("Obtener Todos los Clientes")
    void getClients() {
        Client client1 = new Client(1L, "Juan", "Perez", "j@p.com", false);
        Client client2 = new Client(2L, "Maria", "Lopez", "m@l.com", true);
        List<Client> clients = List.of(client1, client2);

        when(clientService.getClients()).thenReturn(clients);

        webTestClient.get()
                .uri("/client")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Client.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response.size(), clients.size());
                    for (int i = 0; i < response.size(); i++) {
                        assertEquals(response.get(i).getId(), clients.get(i).getId());
                        assertEquals(response.get(i).getName(), clients.get(i).getName());
                        assertEquals(response.get(i).getLastName(), clients.get(i).getLastName());
                        assertEquals(response.get(i).getEmail(), clients.get(i).getEmail());
                        assertEquals(response.get(i).getFrequent(), clients.get(i).getFrequent());
                    }
                });
        Mockito.verify(clientService).getClients();
    }

    @Test
    @DisplayName("Actualizar Cliente")
    void updateClient() {
        Client client = new Client(1L, "Juan", "Perez", "j@p.com", false);

        when(clientService.updateClient(any(Long.class), any(Client.class))).thenReturn(client);

        webTestClient.put()
                .uri("/client/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(client)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Client.class)
                .value(client1 -> {
                    assertNotNull(client1);
                    assertEquals(client1.getId(), client.getId());
                    assertEquals(client1.getName(), client.getName());
                    assertEquals(client1.getLastName(), client.getLastName());
                    assertEquals(client1.getEmail(), client.getEmail());
                    assertEquals(client1.getFrequent(), client.getFrequent());
                });
        Mockito.verify(clientService).updateClient(any(Long.class), any(Client.class));
    }

    @Test
    @DisplayName("Actualizar Cliente - No Encontrado")
    void updateClientNotFound() {
        Client client = new Client(1L, "Juan", "Perez", "j@p.com", false);

        when(clientService.updateClient(any(Long.class), any(Client.class))).thenThrow(new RuntimeException("Cliente con el id 1 no pudo ser actualizado"));

        webTestClient.put()
                .uri("/client/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(client)
                .exchange()
                .expectStatus().isNotFound();
        Mockito.verify(clientService).updateClient(any(Long.class), any(Client.class));
    }

    @Test
    @DisplayName("Eliminar Cliente")
    void deleteClient() {
        Mockito.doNothing().when(clientService).deleteClient(1L);

        webTestClient.delete()
                .uri("/client/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response, "Cliente eliminado exitosamente");
                });
        Mockito.verify(clientService).deleteClient(1L);
    }
}