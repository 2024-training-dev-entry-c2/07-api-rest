package com.api_restaurant.services;

import com.api_restaurant.models.Client;
import com.api_restaurant.repositories.ClientRepository;
import com.api_restaurant.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    private ClientService clientService;
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        clientService = new ClientService(clientRepository);
    }

    @Test
    @DisplayName("Add Client - Success")
    void addClient() {
        Client client = new Client("John", "Doe", "john.doe@example.com", false);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client result = clientService.addClient(client);

        assertEquals(client.getName(), result.getName());
        assertEquals(client.getLastName(), result.getLastName());
        assertEquals(client.getEmail(), result.getEmail());
        assertEquals(client.getFrequent(), result.getFrequent());

        Mockito.verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("Get Client - Found")
    void getClientFound() {
        Client client = new Client("John", "Doe", "john.doe@example.com", false);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.getClient(1L);

        assertTrue(result.isPresent());
        assertEquals(client.getName(), result.get().getName());
        assertEquals(client.getLastName(), result.get().getLastName());
        assertEquals(client.getEmail(), result.get().getEmail());
        assertEquals(client.getFrequent(), result.get().getFrequent());

        Mockito.verify(clientRepository).findById(1L);
    }

    @Test
    @DisplayName("Get Client - Not Found")
    void getClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Client> result = clientService.getClient(1L);

        assertTrue(result.isEmpty());

        Mockito.verify(clientRepository).findById(1L);
    }

    @Test
    @DisplayName("Get Clients - Found")
    void getClientsFound() {
        Client client1 = new Client("John", "Doe", "john.doe@example.com", false);
        Client client2 = new Client("Jane", "Doe", "jane.doe@example.com", true);
        List<Client> clients = List.of(client1, client2);

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getClients();

        assertEquals(2, result.size());
        assertEquals(client1.getName(), result.get(0).getName());
        assertEquals(client2.getName(), result.get(1).getName());

        Mockito.verify(clientRepository).findAll();
    }

    @Test
    @DisplayName("Get Clients - Not Found")
    void getClientsNotFound() {
        when(clientRepository.findAll()).thenReturn(List.of());

        List<Client> result = clientService.getClients();

        assertTrue(result.isEmpty());

        Mockito.verify(clientRepository).findAll();
    }

    @Test
    @DisplayName("Update Client - Success")
    void updateClientSuccess() {
        Client client = new Client("John", "Doe", "john.doe@example.com", false);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client updatedClient = new Client("John", "Smith", "john.smith@example.com", true);
        Client result = clientService.updateClient(1L, updatedClient);

        assertEquals(updatedClient.getName(), result.getName());
        assertEquals(updatedClient.getLastName(), result.getLastName());
        assertEquals(updatedClient.getEmail(), result.getEmail());
        assertEquals(updatedClient.getFrequent(), result.getFrequent());

        Mockito.verify(clientRepository).findById(1L);
        Mockito.verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("Update Client - Not Found")
    void updateClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Client updatedClient = new Client("John", "Smith", "john.smith@example.com", true);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clientService.updateClient(1L, updatedClient);
        });

        assertEquals("Cliente con el id 1 no pudo ser actualizado", exception.getMessage());

        Mockito.verify(clientRepository).findById(1L);
        Mockito.verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("Delete Client - Success")
    void deleteClientSuccess() {
        doNothing().when(clientRepository).deleteById(1L);

        clientService.deleteClient(1L);

        Mockito.verify(clientRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Delete Client - Not Found")
    void deleteClientNotFound() {
        doThrow(new RuntimeException("Cliente con el id 1 no pudo ser eliminado")).when(clientRepository).deleteById(1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clientService.deleteClient(1L);
        });

        assertEquals("Cliente con el id 1 no pudo ser eliminado", exception.getMessage());

        Mockito.verify(clientRepository).deleteById(1L);
    }
}