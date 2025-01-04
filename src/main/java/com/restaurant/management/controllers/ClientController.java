package com.restaurant.management.controllers;

import com.restaurant.management.models.Client;
import com.restaurant.management.services.ClientService;
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
  public ResponseEntity<String> addClient(@RequestBody Client client){
    service.addClient(client);
    return ResponseEntity.ok("Cliente agregado éxitosamente");
  }

  @GetMapping("/{id}")
  public ResponseEntity<Client> getClient(@PathVariable Long id){
    return service.getClientById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Client>> getClients(){
    return ResponseEntity.ok(service.getClients());
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateClient(@PathVariable Long id, @RequestBody Client client){
    try{
      Client updatedClient = service.updateClient(id, client);
      return ResponseEntity.ok("Se ha actualizado exitosamente el cliente");
    } catch (RuntimeException e){
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteClient(@PathVariable Long id){
    service.deleteClient(id);
    return ResponseEntity.noContent().build();
  }
}
