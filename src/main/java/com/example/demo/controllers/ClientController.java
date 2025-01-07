package com.example.demo.controllers;

import com.example.demo.DTO.client.ClientRequestDTO;
import com.example.demo.DTO.client.ClientResponseDTO;
import com.example.demo.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        ClientResponseDTO clientResponseDTO = clientService.createClient(clientRequestDTO);
        return ResponseEntity.ok(clientResponseDTO);
    }

    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<ClientResponseDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        ClientResponseDTO clientResponseDTO = clientService.getClientById(id);
        return ResponseEntity.ok(clientResponseDTO);
    }

    // Actualizar un cliente
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO clientRequestDTO) {
        ClientResponseDTO updatedClient = clientService.updateClient(id, clientRequestDTO);
        return ResponseEntity.ok(updatedClient);
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
