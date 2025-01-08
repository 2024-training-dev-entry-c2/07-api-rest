package com.example.restaurant.controllers;

import com.example.restaurant.dto.client.ClientRequestDTO;
import com.example.restaurant.dto.client.ClientResponseDTO;
import com.example.restaurant.models.Client;
import com.example.restaurant.services.client.AddClientService;
import com.example.restaurant.services.client.DeleteClientService;
import com.example.restaurant.services.client.GetAllClientsService;
import com.example.restaurant.services.client.GetClientByIdService;
import com.example.restaurant.services.client.UpdateClientService;
import com.example.restaurant.utils.converter.ClientDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final AddClientService addClientService;
    private final GetClientByIdService getClientByIdService;
    private final GetAllClientsService getAllClientsService;
    private final UpdateClientService updateClientService;
    private final DeleteClientService deleteClientService;

    @Autowired
    public ClientController(AddClientService addClientService, GetClientByIdService getClientByIdService, GetAllClientsService getAllClientsService, UpdateClientService updateClientService, DeleteClientService deleteClientService) {
        this.addClientService = addClientService;
        this.getClientByIdService = getClientByIdService;
        this.getAllClientsService = getAllClientsService;
        this.updateClientService = updateClientService;
        this.deleteClientService = deleteClientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> addClient(@RequestBody ClientRequestDTO clientRequest) {
        Client clientEntity = ClientDtoConverter.convertToEntity(clientRequest);
        addClientService.execute(clientEntity);
        ClientResponseDTO responseDTO = ClientDtoConverter.convertToDto(clientEntity);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        Client client = getClientByIdService.execute(id);
        ClientResponseDTO responseDTO = ClientDtoConverter.convertToDto(client);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<Client> clients = getAllClientsService.execute();
        List<ClientResponseDTO> responseDTOs = clients.stream()
                .map(ClientDtoConverter::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO client) {
        Client clientEntity = ClientDtoConverter.convertToEntity(client);
        Client updatedClient = updateClientService.execute(id, clientEntity);
        ClientResponseDTO responseDTO = ClientDtoConverter.convertToDto(updatedClient);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        deleteClientService.execute(id);
        return ResponseEntity.noContent().build();
    }

}
