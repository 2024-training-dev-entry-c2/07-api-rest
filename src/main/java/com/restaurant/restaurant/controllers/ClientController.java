package com.restaurant.restaurant.controllers;

import com.restaurant.restaurant.dtos.ClientDTO;
import com.restaurant.restaurant.models.ClientModel;
import com.restaurant.restaurant.services.ClientService;
import com.restaurant.restaurant.utils.MapperUtil;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
  @Autowired
  private ClientService clientService;

  @PostMapping
  public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO){
    ClientModel clientModel = MapperUtil.mapToClientModel(clientDTO);
    ClientModel createdClient = clientService.createClient(clientModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(MapperUtil.mapToClientDTO(createdClient));
  }

  @GetMapping
  public ResponseEntity<List<ClientDTO>> getClients(){
    List<ClientModel> clients = clientService.getClients();
    List<ClientDTO> clientDTOs = clients.stream().map(MapperUtil::mapToClientDTO).collect(Collectors.toList());
    return ResponseEntity.ok(clientDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO){
    ClientModel client = MapperUtil.mapToClientModel(clientDTO);
    ClientModel updatedClient = clientService.updateClient(id, client);
    return ResponseEntity.ok(MapperUtil.mapToClientDTO(updatedClient));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable Long id){
    clientService.deleteClient(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("{id}/verify-frecuent")
  public ResponseEntity<Void> verifyFrecuent(@PathVariable Long id){
    clientService.verifyFrecuent(id);
    return ResponseEntity.ok().build();
  }
}
