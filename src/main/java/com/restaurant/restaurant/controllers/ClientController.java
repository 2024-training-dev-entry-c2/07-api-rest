package com.restaurant.restaurant.controllers;

import com.restaurant.restaurant.dtos.ClientDTO;
import com.restaurant.restaurant.enums.ClientType;
import com.restaurant.restaurant.services.ClientService;
import com.restaurant.restaurant.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
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
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

  private final ClientService clientService;

 @GetMapping
  public ResponseEntity<ApiResponse<List<ClientDTO>>> getAllClients(){
    List<ClientDTO> clients = clientService.findAll();
    return ResponseEntity.ok(ApiResponse.success(clients));
 }

 @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ClientDTO>> getClientById(@PathVariable Long id) {
   ClientDTO client = clientService.findById(id);
   return ResponseEntity.ok(ApiResponse.success(client));
 }

 @PostMapping
  public ResponseEntity<ApiResponse<ClientDTO>> createClient(@RequestBody ClientDTO clientDTO){
    ClientDTO createdClient = clientService.createClient(clientDTO);
    return new ResponseEntity<>(ApiResponse.success("Success Created Client", createdClient), HttpStatus.CREATED);
 }

 @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ClientDTO>> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO){
    ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
    return new ResponseEntity<>(ApiResponse.success("Success Updated Client", updatedClient), HttpStatus.OK);
 }

  @PutMapping("/{id}/type")
  public ResponseEntity<ApiResponse<ClientDTO>> updateClientType(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
    String typeString = requestBody.get("type");

    ClientType newType;
    try {
      newType = ClientType.valueOf(typeString.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid client type: " + typeString);
    }
    ClientDTO updatedClient = clientService.updateClientType(id, newType);

    return ResponseEntity.ok(ApiResponse.success("Success Updated Client Type", updatedClient));
  }

 @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable Long id){
    clientService.deleteClient(id);
    return new ResponseEntity<>(ApiResponse.success("Success Deleted Client", null), HttpStatus.NO_CONTENT);
 }
}
