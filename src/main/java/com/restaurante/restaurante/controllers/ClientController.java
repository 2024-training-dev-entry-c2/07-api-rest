package com.restaurante.restaurante.controllers;


import com.restaurante.restaurante.dto.ClientDTO;
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
import com.restaurante.restaurante.services.IClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")

public class ClientController {

    private final IClientService IClientService;

    @Autowired
    public ClientController(IClientService IClientService){
        this.IClientService = IClientService;
    }


    @PostMapping
        public ResponseEntity<String> addClient(@RequestBody ClientDTO clientDTO){

        IClientService.addClient(clientDTO);
       return ResponseEntity.ok("Cliente agregado exitosamente");
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id){

        return ResponseEntity.ok(IClientService.getClient(id).orElseThrow());
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getClients(){
        return ResponseEntity.ok(IClientService.getClients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO){
        try{
            ClientDTO clientUpdated = IClientService.updateClient(id, clientDTO);
            return ResponseEntity.ok("Se ha actualizado exitosamente el cliente");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteClient(@PathVariable Long id){
            IClientService.deleteClient(id);
            return ResponseEntity.noContent().build();
        }


        @GetMapping("/{id}/status")
        public ResponseEntity<String> checkAndUpdateClientStatus(@PathVariable Long id) {
            String status = IClientService.checkAndUpdateClientStatus(id);
            return ResponseEntity.ok(status);
        }

}
