package com.restaurant.management.services;

import com.restaurant.management.models.Client;
import com.restaurant.management.repositories.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceTest {
  private final ClientRepository clientRepository;
  private final ClientService clientService;

  ClientServiceTest() {
    this.clientRepository = mock(ClientRepository.class);
    clientService = new ClientService(clientRepository);
  }

  @Test
  @DisplayName("Agregar cliente")
  void addClient() {
    Client client = new Client(1L, "name", "email");
    when(clientRepository.save(eq(client))).thenReturn(client);

    Client actualClient = clientService.addClient(client);

    assertEquals(client.getId(), actualClient.getId());
    assertEquals(client.getName(), actualClient.getName());
    assertEquals(client.getEmail(), actualClient.getEmail());
    assertEquals(client.getFrequent(), actualClient.getFrequent());

    verify(clientRepository).save(client);
  }

  @Test
  @DisplayName("Obtener cliente por id")
  void getClientById() {
    Client client = new Client(1L, "name", "email");
    when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

    Optional<Client> retrievedClient = clientService.getClientById(1L);

    assertTrue(retrievedClient.isPresent());
    assertEquals(client.getId(), retrievedClient.get().getId());

    verify(clientRepository).findById(anyLong());
  }

  @Test
  @DisplayName("Obtener lista de clientes")
  void getClients() {
    when(clientRepository.findAll()).thenReturn(getClientList());

    List<Client> retrievedClients = clientService.getClients();

    assertEquals(getClientList().size(), retrievedClients.size());
    assertEquals(getClientList().get(0).getId(), retrievedClients.get(0).getId());
    assertEquals(getClientList().get(1).getId(), retrievedClients.get(1).getId());
    assertEquals(getClientList().get(2).getId(), retrievedClients.get(2).getId());

    verify(clientRepository).findAll();
  }

  @Test
  @DisplayName("Actualizar cliente exitoso")
  void updateClient() {
    Client client = new Client(1L, "name", "email");
    Client updatedClient = new Client("Updated Name", "updated@example.com");
    when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
    when(clientRepository.save(eq(client))).thenAnswer(invocation -> invocation.getArgument(0));

    Client result = clientService.updateClient(1L, updatedClient);

    assertEquals("Updated Name", result.getName());
    assertEquals("updated@example.com", result.getEmail());

    verify(clientRepository).findById(anyLong());
    verify(clientRepository).save(client);
  }

  @Test
  void deleteClient() {
    doNothing().when(clientRepository).deleteById(anyLong());

    clientService.deleteClient(1L);

    verify(clientRepository).deleteById(anyLong());
  }

  private List<Client> getClientList() {
    return List.of(new Client(1L, "A", "Ve"),
      new Client(2L, "B", "Ce"),
      new Client(3L, "C", "De"));
  }
}