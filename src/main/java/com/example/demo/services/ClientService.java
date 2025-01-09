package com.example.demo.services;

import com.example.demo.models.Client;
import com.example.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(Client client) {

        if (client.getIsOften() == null) {
            client.setIsOften(false);
        }

        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();

    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public Client updateClient(Long id, Client client) {
        Client existingClient = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        return clientRepository.save(existingClient);
    }


    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found");
        }
        clientRepository.deleteById(id);
    }

}
