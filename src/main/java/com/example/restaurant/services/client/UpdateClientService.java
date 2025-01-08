package com.example.restaurant.services.client;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Client;
import com.example.restaurant.observers.ClientSubject;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommandModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateClientService implements ICommandModifier<Client, Client> {

    private final IClientRepository clientRepository;
    private final ClientSubject clientSubject;

    @Autowired
    public UpdateClientService(IClientRepository clientRepository, ClientSubject clientSubject) {
        this.clientRepository = clientRepository;
        this.clientSubject = clientSubject;
    }

    @Override
    public Client execute(Long id, Client client) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con el id " + id + " no encontrado"));

        existingClient.setName(client.getName());
        existingClient.setLastName(client.getLastName());
        existingClient.setEmail(client.getEmail());
        Client updatedClient = clientRepository.save(existingClient);

        clientSubject.notifyObservers(EventType.UPDATE, updatedClient);
        return updatedClient;
    }
}
