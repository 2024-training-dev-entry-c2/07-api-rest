package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientServiceTest {

  private ClientRepository clientRepository;
  private ClientService clientService;

  private Client newClient;
  private Client client;
  private Client client2;
  private List<Client> clients;
  private Client updatedClient;
  private Client savedClient;


  @BeforeEach
  void setUp() {
    clientRepository = mock(ClientRepository.class);
    clientService = new ClientService(clientRepository);

    newClient = new Client(null, "Juan", "Perez", "juanperez@gmail.com", "123456789", "Av. de la Independencia, 1", true);
    client = new Client(1L,"Juan","Perez","juanperez@gmail.com","123456789","Av. de la Independencia, 1",true);
    client2 = new Client(2L, "Maria", "Lopez", "marialopez@gmail.com", "987654321", "Avenida Siempre Viva 742", false);
    clients = List.of(client, client2);
    updatedClient = new Client(null, "Juan Carlos", "Perez Lopez", "juancarlosperez@gmail.com", "1234", "Nueva Direccion 456", false);
    savedClient = new Client(1L, "Juan Carlos", "Perez Lopez", "juancarlosperez@gmail.com", "1234", "Nueva Direccion 456", false);
  }

  @Test
  @DisplayName("Save client")
  void saveClient() {
    when(clientRepository.save(newClient)).thenReturn(client);
    Client result = clientService.saveClient(newClient);
    assertNotNull(result);
    assertEquals(client.getId(), result.getId());
    Mockito.verify(clientRepository, Mockito.times(1)).save(newClient);
  }

  @Test
  @DisplayName("Get client")
  void getClient() {
    when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
    Optional<Client> result = clientService.getClient(1L);
    assertTrue(result.isPresent());
    assertEquals(client.getId(), result.get().getId());
    Mockito.verify(clientRepository, Mockito.times(1)).findById(1L);
  }

  @Test
  @DisplayName("List clients")
  void listClients() {
    when(clientRepository.findAll()).thenReturn(clients);
    List<Client> result = clientService.listClients();
    assertNotNull(result);
    assertEquals(clients.size(), result.size());
    Mockito.verify(clientRepository, Mockito.times(1)).findAll();
  }

  @Test
  @DisplayName("Update client")
  void updateClient() {
    when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
    when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

    Client result = clientService.updateClient(1L, updatedClient);

    assertEquals(savedClient.getId(), result.getId());
    assertEquals("Juan Carlos", result.getName());
    assertEquals("Perez Lopez", result.getLastName());
    assertEquals("juancarlosperez@gmail.com", result.getEmail());

    Mockito.verify(clientRepository, Mockito.times(1)).findById(1L);
    Mockito.verify(clientRepository, Mockito.times(1)).save(any(Client.class));
  }

  @Test
  @DisplayName("Update client - Client not found")
  void updateClient_notFound() {
    when(clientRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> clientService.updateClient(1L, new Client()));
    assertEquals("Client con el id 1 no pudo ser actualizado", exception.getMessage());
    Mockito.verify(clientRepository, Mockito.times(1)).findById(1L);
    Mockito.verify(clientRepository, Mockito.never()).save(any(Client.class));
  }

  @Test
  @DisplayName("Delete client")
  void deleteClient() {
    doNothing().when(clientRepository).deleteById(1L);
    clientService.deleteClient(1L);
    Mockito.verify(clientRepository, Mockito.times(1)).deleteById(1L);
  }
}