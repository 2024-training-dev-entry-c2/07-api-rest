package com.api_restaurant.services;

import com.api_restaurant.models.Client;
import com.api_restaurant.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public void addClient(Client client){
        clientRepository.save(client);
    }

    public Optional<Client> getClient(Long id){
        return clientRepository.findById(id);
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(Long id, Client client){
        return clientRepository.findById(id).map(x -> {
            x.setName(client.getName());
            x.setLastName(client.getLastName());
            x.setEmail(client.getEmail());
            return clientRepository.save(x);
        }).orElseThrow(() -> {
            return new RuntimeException("Cliente con el id " + id + " no pudo ser actualizado");
        });
    }

    public void deleteClient(Long id){
        clientRepository.deleteById(id);
    }



}
