package com.example.demo.controllers;

import com.example.demo.DTO.client.ClientResponseDTO;
import com.example.demo.models.Client;
import com.example.demo.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ClientController.class)
class ClientControllerTest {

    @MockBean
    private ClientService clientService;

    @Autowired
    private WebTestClient webTestClient;

    private Client client;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(1L)
                .name("Mario")
                .email("Mario@bros.com")
                .isOften(false)
                .build();
    }

    @DisplayName("Crear cliente")
    @Test
    void createClient() {
        when(clientService.createClient(any(Client.class))).thenReturn(client);
        webTestClient.post()
                .uri("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(client)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ClientResponseDTO.class)
                .value(clientdto -> {
                    assertEquals(clientdto.getName(), client.getName());

                });
        verify(clientService).createClient(any(Client.class));

    }

    @Test
    @DisplayName("obtener cliente")
    void getClientById() {
        when(clientService.getClientById(anyLong())).thenReturn(client);
        webTestClient.get()
                .uri("/clients/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ClientResponseDTO.class)
                .value(clientFound -> {
                    assertEquals(client.getId(), clientFound.getId());
                    assertEquals(client.getName(), clientFound.getName());
                    assertEquals(client.getIsOften(), clientFound.getIsOften());
                    assertEquals(client.getEmail(), clientFound.getEmail());
                });
        verify(clientService).getClientById(anyLong());
    }

    @Test
    void getAllClients() {
        when(clientService.getAllClients()).thenReturn(getClientsList());
        webTestClient.get()
                .uri("/clients")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ClientResponseDTO.class)
                .hasSize(3)
                .value(clientF->{
                    assertEquals("Mario", clientF.get(0).getName());
                    assertEquals("Luigui", clientF.get(1).getName());
                    assertEquals("Fredy", clientF.get(2).getName());
                });
        verify(clientService).getAllClients();

    }

    @Test
    void updateClient() {
        when(clientService.updateClient(anyLong(),any(Client.class))).thenReturn(client);
        webTestClient.put()
                .uri("/clients/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(client)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ClientResponseDTO.class)
                .value(p->{
                    assertEquals(client.getId(),p.getId());
                    assertEquals(client.getName(),p.getName());
                    assertEquals(client.getEmail(),p.getEmail());
                    assertEquals(client.getIsOften(),p.getIsOften());
                });
        verify(clientService).updateClient(anyLong(),any(Client.class));
    }

    @Test
    void deleteClient() {
        doNothing().when(clientService).deleteClient(anyLong());
        webTestClient.delete()
                .uri("/clients/{id}",1L)
                .exchange()
                .expectStatus().isNoContent();

        verify(clientService).deleteClient(anyLong());
    }

    private List<Client> getClientsList() {
        return List.of(
                Client.builder()
                        .id(1L)
                        .name("Mario")
                        .email("Mario@bros.com")
                        .isOften(false)
                        .build(),
                Client.builder()
                        .id(1L)
                        .name("Luigui")
                        .email("Luigi@bros.com")
                        .isOften(false)
                        .build(),
                Client.builder()
                        .id(1L)
                        .name("Fredy")
                        .email("Fredy@M.com")
                        .isOften(false)
                        .build()

        );
    }
}