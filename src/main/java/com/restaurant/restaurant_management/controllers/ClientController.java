package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ResponseEntity<String> saveClient(@RequestBody Client client) {
    clientService.saveClient(client);
    return ResponseEntity.ok("Cliente creado con éxito");
  }

  @GetMapping
  public ResponseEntity<List<Client>> getClients() {
    return ResponseEntity.ok(clientService.listClients());
  }
}
