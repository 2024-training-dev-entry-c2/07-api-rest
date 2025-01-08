package com.example.restaurant.services.client;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Client;
import com.example.restaurant.observers.ClientSubject;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteClientService implements ICommandParametrized<Void, Long> {

    private final IClientRepository clientRepository;
    private final ClientSubject clientSubject;

    @Autowired
    public DeleteClientService(IClientRepository clientRepository, ClientSubject clientSubject) {
        this.clientRepository = clientRepository;
        this.clientSubject = clientSubject;
    }

    @Override
    public Void execute(Long id) {
        Client clientToDelete = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con el id " + id + " no encontrado"));

        clientRepository.deleteById(id);
        clientSubject.notifyObservers(EventType.DELETE, clientToDelete);
        return null;
    }
}
