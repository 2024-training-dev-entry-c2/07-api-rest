package com.api_restaurant.controllers;

import com.api_restaurant.dto.client.ClientRequestDTO;
import com.api_restaurant.dto.client.ClientResponseDTO;
import com.api_restaurant.models.Client;
import com.api_restaurant.services.ClientService;
import com.api_restaurant.utils.ClientDtoConvert;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> addClient(@RequestBody ClientRequestDTO clientRequestDTO){
        Client client = new Client(
                clientRequestDTO.getName(),
                clientRequestDTO.getLastName(),
                clientRequestDTO.getEmail()
        );
        service.addClient(client);
        return ResponseEntity.ok("Cliente creado exitosamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClient(@PathVariable Long id){
        return service.getClient(id)
                .map(client -> ResponseEntity.ok(ClientDtoConvert.convertToResponseDto(client)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getClients(){
        List<Client> clients = service.getClients();
        List<ClientResponseDTO> response = clients.stream()
                .map(ClientDtoConvert::convertToResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO clientRequestDTO){
      try{
        Client updatedClient = service.updateClient(id, new Client(
                clientRequestDTO.getName(),
                clientRequestDTO.getLastName(),
                clientRequestDTO.getEmail()
        ));
        return ResponseEntity.ok(ClientDtoConvert.convertToResponseDto(updatedClient));
      }catch(RuntimeException e){
        return ResponseEntity.notFound().build();
      }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id){
        service.deleteClient(id);
        return ResponseEntity.ok("Cliente eliminado exitosamente");
    }
}
