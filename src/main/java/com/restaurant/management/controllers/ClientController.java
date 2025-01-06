package com.restaurant.management.controllers;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.dto.ClientRequestDTO;
import com.restaurant.management.models.dto.ClientResponseDTO;
import com.restaurant.management.services.ClientService;
import com.restaurant.management.utils.DtoClientConverter;
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

@RestController
@RequestMapping("api/clientes")
public class ClientController {
  private final ClientService service;

  @Autowired
  public ClientController(ClientService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<String> addClient(@RequestBody ClientRequestDTO clientRequest){
    Client client = DtoClientConverter.toClient(clientRequest);
    service.addClient(client);
    return ResponseEntity.ok("Cliente agregado éxitosamente");
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientResponseDTO> getClient(@PathVariable Long id){
    return service.getClientById(id)
      .map(client -> ResponseEntity.ok(DtoClientConverter.toClientResponseDTO(client)))
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<ClientResponseDTO>> getClients(){
    List<ClientResponseDTO> clients = service.getClients().stream()
      .map(DtoClientConverter::toClientResponseDTO)
      .toList();
    return ResponseEntity.ok(clients);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO clientRequest){
    try{
      Client updatedClient = service.updateClient(id, DtoClientConverter.toClient(clientRequest));
      return ResponseEntity.ok(DtoClientConverter.toClientResponseDTO(updatedClient));
    } catch (RuntimeException e){
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable Long id){
    service.deleteClient(id);
    return ResponseEntity.noContent().build();
  }
}
