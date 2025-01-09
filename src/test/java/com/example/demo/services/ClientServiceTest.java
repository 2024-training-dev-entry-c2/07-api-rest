package com.example.demo.services;

import com.example.demo.models.Client;
import com.example.demo.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;
    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = Client.builder()
                .name("Mario")
                .email("mario@bros.com")
                .build();
    }

    @Test
    void createClient() {
        when(clientRepository.save(client)).thenReturn(client);
        // Act
        Client result = clientService.createClient(client);
        // Assert
        assertNotNull(result);
        assertFalse(result.getIsOften());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void getAllClients() {
        when(clientRepository.findAll()).thenReturn(getClientsList());
        List<Client> clients= clientService.getAllClients();
        assertNotNull(clients);
        assertEquals(3, clients.size());
        assertEquals("Mario", clients.get(0).getName());
        assertEquals("Luigui", clients.get(1).getName());
        verify(clientRepository).findAll();

    }

    @Test
    void getClientById() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        Client result = clientService.getClientById(clientId);
        assertNotNull(result);
        assertEquals("Mario",result.getName());
        verify(clientRepository).findById(clientId);
    }

    @Test
    void updateClient() {
        Long clientId = 1L;

        Client updatedClient = new Client();
        updatedClient.setName("Luigui");
        updatedClient.setEmail("luigui@example.com");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        Client result= clientService.updateClient(clientId,updatedClient);
        assertNotNull(result);
        assertEquals("Luigui", result.getName());
        assertEquals("luigui@example.com", result.getEmail());
        verify(clientRepository).findById(clientId);
        verify(clientRepository).save(client);
    }

    @Test
    void deleteClient() {
        Long clientId = 1L;
        when(clientRepository.existsById(clientId)).thenReturn(true);
        clientService.deleteClient(clientId);

        verify(clientRepository).existsById(clientId);
        verify(clientRepository).deleteById(clientId);
    }
    @Test
    void deleteClientNotFound(){
        Long clientId = 1L;
        when(clientRepository.existsById(clientId)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientService.deleteClient(clientId));
        assertEquals("Client not found", exception.getMessage());
        verify(clientRepository, times(1)).existsById(clientId);
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