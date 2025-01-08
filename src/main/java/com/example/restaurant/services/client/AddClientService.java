package com.example.restaurant.services.client;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Client;
import com.example.restaurant.observers.ClientSubject;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddClientService implements ICommandParametrized<Client, Client> {

    private final IClientRepository clientRepository;
    private final ClientSubject clientSubject;

    @Autowired
    public AddClientService(IClientRepository clientRepository, ClientSubject clientSubject) {
        this.clientRepository = clientRepository;
        this.clientSubject = clientSubject;
    }

    @Override
    public Client execute(Client client) {
        Client savedClient = clientRepository.save(client);
        clientSubject.notifyObservers(EventType.CREATE, savedClient);
        return savedClient;
    }
}
