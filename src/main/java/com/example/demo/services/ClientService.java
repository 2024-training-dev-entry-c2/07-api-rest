package com.example.demo.services;

import com.example.demo.models.Client;
import com.example.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public void addClient(Client client) {
        repository.save(client);
    }
    public Optional<Client> findClientById(Long id){
        return repository.findById(id);
    }
    public List<Client> findAllClients(){
        return repository.findAll();
    }
    public void removeClient(Long id){
        repository.deleteById(id);
    }
}
