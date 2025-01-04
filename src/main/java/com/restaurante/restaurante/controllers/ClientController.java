package com.restaurante.restaurante.controllers;


import com.restaurante.restaurante.models.Client;
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
import com.restaurante.restaurante.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")

public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }


    @PostMapping
    public ResponseEntity<String> addClient(@RequestBody  Client client){
       clientService.addClient(client);
       return ResponseEntity.ok("Cliente agregado exitosamente");
    }


    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id){
        return ResponseEntity.ok(clientService.getClient(id).orElseThrow());
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients(){
        return ResponseEntity.ok(clientService.getClients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClient(@PathVariable Long id, @RequestBody Client client){
        try{
            Client clientUpdated = clientService.updateClient(id, client);
            return ResponseEntity.ok("Se ha actualizado exitosamente el cliente");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteClient(@PathVariable Long id){
            clientService.deleteClient(id);
            return ResponseEntity.noContent().build();
        }


        @GetMapping("/{id}/status")
        public ResponseEntity<String> checkAndUpdateClientStatus(@PathVariable Long id) {
            String status = clientService.checkAndUpdateClientStatus(id);
            return ResponseEntity.ok(status);
        }

}
