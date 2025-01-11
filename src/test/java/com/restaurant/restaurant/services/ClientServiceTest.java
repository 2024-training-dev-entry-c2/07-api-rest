package com.restaurant.restaurant.services;

import com.restaurant.restaurant.dtos.ClientDTO;
import com.restaurant.restaurant.exceptions.ResourceNotFoundException;
import com.restaurant.restaurant.models.ClientModel;
import com.restaurant.restaurant.repositories.IClientRepository;
import com.restaurant.restaurant.enums.ClientType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

  @Mock
  private IClientRepository IClientRepository;

  @InjectMocks
  private ClientService clientService;

  private ClientModel clientModel;
  private ClientDTO clientDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    clientModel = new ClientModel();
    clientModel.setId(1L);
    clientModel.setName("John");
    clientModel.setLastName("Doe");
    clientModel.setEmail("john.doe@example.com");
    clientModel.setPhone("123456789");
    clientModel.setType(ClientType.COMUN);

    clientDTO = new ClientDTO(1L, "John", "Doe", "john.doe@example.com", "123456789", ClientType.COMUN);
  }

  @Test
  @DisplayName("Test findAll() - Should return all clients")
  void findAll() {
    when(IClientRepository.findAll()).thenReturn(List.of(clientModel));

    List<ClientDTO> clientDTOs = clientService.findAll();

    assertNotNull(clientDTOs);
    assertEquals(1, clientDTOs.size());
    assertEquals(clientDTO.getName(), clientDTOs.get(0).getName());
  }

  @Test
  @DisplayName("Test findById() - Should return client by id")
  void findById() {
    when(IClientRepository.findById(anyLong())).thenReturn(Optional.of(clientModel));

    ClientDTO foundClient = clientService.findById(1L);

    assertNotNull(foundClient);
    assertEquals(clientDTO.getName(), foundClient.getName());
  }

  @Test
  @DisplayName("Test findById() - Should throw ResourceNotFoundException if client does not exist")
  void findByIdNotFound() {
    when(IClientRepository.findById(anyLong())).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      clientService.findById(999L);
    });

    assertEquals("Client not found with id 999", exception.getMessage());
  }

  @Test
  @DisplayName("Test createClient() - Should create and return a client")
  void createClient() {
    when(IClientRepository.save(any(ClientModel.class))).thenReturn(clientModel);

    ClientDTO createdClient = clientService.createClient(clientDTO);

    assertNotNull(createdClient);
    assertEquals(clientDTO.getName(), createdClient.getName());
    assertEquals(clientDTO.getEmail(), createdClient.getEmail());
  }

  @Test
  @DisplayName("Test updateClient() - Should update and return the client")
  void updateClient() {
    when(IClientRepository.findById(anyLong())).thenReturn(Optional.of(clientModel));
    when(IClientRepository.save(any(ClientModel.class))).thenReturn(clientModel);

    clientDTO.setPhone("987654321");
    ClientDTO updatedClient = clientService.updateClient(1L, clientDTO);

    assertNotNull(updatedClient);
    assertEquals(clientDTO.getPhone(), updatedClient.getPhone());
  }

  @Test
  @DisplayName("Test updateClient() - Should throw ResourceNotFoundException if client does not exist")
  void updateClientNotFound() {
    when(IClientRepository.findById(anyLong())).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      clientService.updateClient(999L, clientDTO);
    });

    assertEquals("Client not found with id 999", exception.getMessage());
  }

  @Test
  @DisplayName("Test deleteClient() - Should delete a client successfully")
  void deleteClient() {
    when(IClientRepository.existsById(anyLong())).thenReturn(true);
    doNothing().when(IClientRepository).deleteById(anyLong());

    clientService.deleteClient(1L);

    verify(IClientRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Test deleteClient() - Should throw ResourceNotFoundException if client does not exist")
  void deleteClientNotFound() {
    when(IClientRepository.existsById(anyLong())).thenReturn(false);

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      clientService.deleteClient(999L);
    });

    assertEquals("Client not found with id 999", exception.getMessage());
  }
}
