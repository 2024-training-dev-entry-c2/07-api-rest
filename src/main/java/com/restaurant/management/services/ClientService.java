package com.restaurant.management.services;

import com.restaurant.management.models.Client;
import com.restaurant.management.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
  private final ClientRepository repository;

  @Autowired
  public ClientService(ClientRepository repository) {
    this.repository = repository;
  }

  public void addClient(Client client){
    repository.save(client);
  }

  public Optional<Client> getClientById(Long id){
    return repository.findById(id);
  }

  public List<Client> getClients(){
    return repository.findAll();
  }

  public Client updateClient(Long id, Client updatedClient){
    return repository.findById(id).map(c ->{
      c.setName(updatedClient.getName());
      c.setEmail(updatedClient.getEmail());
      return repository.save(c);
    }).orElseThrow(()-> new RuntimeException("Cliente con id " + id + " no se pudo actualizar."));
  }

  public void deleteClient(Long id){
    repository.deleteById(id);
  }


}
