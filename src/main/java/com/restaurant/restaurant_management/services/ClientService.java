package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public void saveClient(Client client) {
    clientRepository.save(client);
  }

  public List<Client> listClients() {
    return clientRepository.findAll();
  }
}
