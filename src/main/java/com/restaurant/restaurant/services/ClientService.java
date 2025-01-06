package com.restaurant.restaurant.services;

import com.restaurant.restaurant.models.ClientModel;
import com.restaurant.restaurant.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
  @Autowired
  private ClientRepository clientRepository;

  public ClientModel createClient(ClientModel client){
    return clientRepository.save(client);
  }

  public List<ClientModel> getClients(){
    return clientRepository.findAll();
  }

  public ClientModel updateClient(Long id, ClientModel client) {
    return clientRepository.findById(id).map(x -> {
      x.setName(client.getName());
      x.setLastName(client.getLastName());
      x.setEmail(client.getEmail());
      x.setPhone(client.getPhone());
      x.setIsFrecuent(client.getIsFrecuent());
      return clientRepository.save(x);
    }).orElseThrow(() -> new RuntimeException("Client with id " + id + " not found"));
  }

  public void deleteClient(Long id){
    clientRepository.deleteById(id);
  }

  public void verifyFrecuent(Long clientId){
    Integer totalOrders = clientRepository.countOrdersByClientId(clientId);
    if(totalOrders >= 10){
      ClientModel client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client with id " + clientId + " not found"));
      client.setIsFrecuent(true);
      clientRepository.save(client);
    }
  }
}
