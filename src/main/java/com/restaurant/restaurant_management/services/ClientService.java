package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public void saveClient(Client client) {
    clientRepository.save(client);
  }

  public Optional<Client> getClient(Long id) {
    return clientRepository.findById(id);
  }

  public List<Client> listClients() {
    return clientRepository.findAll();
  }

  public Client updateClient(Long id, Client client) {
    return clientRepository.findById(id).map(x -> {
      x.setName(client.getName());
      x.setLastName(client.getLastName());
      x.setEmail(client.getEmail());
      x.setPhone(client.getPhone());
      x.setAddress(client.getAddress());
      x.setIsFrequent(client.getIsFrequent());
      return clientRepository.save(x);
    }).orElseThrow(()-> new RuntimeException("Client con el id "+id+" no pudo ser actualizado"));
  }

  public void deleteClient(Long id) {
    clientRepository.deleteById(id);
  }

}
