package com.example.demo.controllers;

import com.example.demo.DTO.ClientDTO;
import com.example.demo.models.Client;
import com.example.demo.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addClient(@RequestBody ClientDTO clientDTO) {
        if (clientDTO.getEmail().isBlank() && clientDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        service.addClient(Client.builder().name(clientDTO.getName())
                .email(clientDTO.getEmail())
                .build());
        return ResponseEntity.ok("Todo oka");
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findClientById(@PathVariable Long id) {
        Optional<Client> clientOptional = service.findClientById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            ClientDTO clientDTO = ClientDTO.builder()
                    .id(client.getId())
                    .name(client.getName())
                    .email(client.getEmail())
                    .build();
            return ResponseEntity.ok(clientDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll() {
        List<ClientDTO> clientDTOS = service.findAllClients()
                .stream().map(client -> ClientDTO.builder()
                        .id(client.getId())
                        .name(client.getName())
                        .email(client.getEmail())
                        .build())
                .toList();
        return ResponseEntity.ok(clientDTOS);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeClient(@PathVariable Long id) {
        if (service.findClientById(id).isPresent()) {
            service.removeClient(id);
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.notFound().build();
    }

}

