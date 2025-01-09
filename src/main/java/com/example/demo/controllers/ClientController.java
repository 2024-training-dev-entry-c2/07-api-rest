package com.example.demo.controllers;

import com.example.demo.DTO.client.ClientRequestDTO;
import com.example.demo.DTO.client.ClientResponseDTO;
import com.example.demo.DTO.converterDTO.ClientConverter;
import com.example.demo.models.Client;
import com.example.demo.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Crear un nuevo cliente

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDTO createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        Client client = ClientConverter.toEntity(clientRequestDTO);
        return ClientConverter.toResponseDTO(clientService.createClient(client));
    }

    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients.stream()
                .map(ClientConverter::toResponseDTO)
                .collect(Collectors.toList()));
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(ClientConverter.toResponseDTO(clientService.getClientById(id)));
    }

    // Actualizar un cliente
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO clientRequestDTO) {
        Client client = ClientConverter.toEntity(clientRequestDTO);

        return ResponseEntity.ok(ClientConverter.toResponseDTO(clientService.updateClient(id, client)));
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
