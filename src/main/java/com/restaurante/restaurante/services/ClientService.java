package com.restaurante.restaurante.services;

import com.restaurante.restaurante.models.Client;
import com.restaurante.restaurante.models.Orders;
import com.restaurante.restaurante.repositories.ClientRepository;
import com.restaurante.restaurante.repositories.OrderRepository;
import com.restaurante.restaurante.utils.UserType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;


    @Autowired
    public ClientService(ClientRepository clientRepository,
                         OrderRepository orderRepository) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
    }


    public void addClient(Client client){
        clientRepository.save(client);
    }

    public Optional<Client> getClient(Long id){
        return clientRepository.findById(id);
    }

    public List<Client> getClients(){
       return clientRepository.findAll();
    }

    public void deleteClient(Long id){
        clientRepository.deleteById(id);
    }

    public Client updateClient(Long id, Client clientUpdated){
       return clientRepository.findById(id).map(x -> {
            x.setName(clientUpdated.getName());
            x.setEmail(clientUpdated.getEmail());
            x.setUserType(clientUpdated.getUserType());
           return clientRepository.save(x);
        }).orElseThrow(() -> new RuntimeException("El cliente con el id " + id +"no pude ser actualizado"));

    }

    @Transactional
    public String checkAndUpdateClientStatus(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        long orderCount = orderRepository.countByClientId(clientId);

        if (orderCount >= 10 && "REGULAR".equals(client.getUserType())) {
            client.setUserType("FREQUENT");
            clientRepository.save(client);
        }
        return client.getUserType();
    }


    public List<Orders> getOrdersByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId, Sort.by(Sort.Direction.ASC, "orderDate"));
    }

}
