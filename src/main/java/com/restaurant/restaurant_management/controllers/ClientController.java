package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.dto.ClientRequestDTO;
import com.restaurant.restaurant_management.dto.ClientResponseDTO;
import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.services.ClientService;
import com.restaurant.restaurant_management.utils.DtoClientConverter;
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

@RestController
@RequestMapping("/api/client")
public class ClientController {
  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping
  public ResponseEntity<String> saveClient(@RequestBody ClientRequestDTO clientRequestDTO) {
    Client client = DtoClientConverter.convertToClient(clientRequestDTO);
    clientService.saveClient(client);
    return ResponseEntity.ok("Cliente creado con éxito");
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientResponseDTO> getClient(@PathVariable Long id) {
    return clientService.getClient(id)
        .map(client -> ResponseEntity.ok(DtoClientConverter.convertToResponseDTO(client)))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<ClientResponseDTO>> getClients() {
    List<Client> clients = clientService.listClients();
    List<ClientResponseDTO> response = clients.stream()
        .map(DtoClientConverter::convertToResponseDTO)
        .toList();
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO clientRequestDTO) {
    try {
      Client updated = clientService.updateClient(id, DtoClientConverter.convertToClient(clientRequestDTO));
      return ResponseEntity.ok(DtoClientConverter.convertToResponseDTO(updated));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable Long id){
    clientService.deleteClient(id);
    return ResponseEntity.noContent().build();
  }

}
