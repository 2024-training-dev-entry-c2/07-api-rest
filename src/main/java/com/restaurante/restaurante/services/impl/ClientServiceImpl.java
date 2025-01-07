package com.restaurante.restaurante.services.impl;

import com.restaurante.restaurante.dto.ClientDTO;
import com.restaurante.restaurante.mapper.ClientMapper;
import com.restaurante.restaurante.models.Client;
import com.restaurante.restaurante.models.Orders;
import com.restaurante.restaurante.observer.OrderSubject;
import com.restaurante.restaurante.repositories.ClientRepository;
import com.restaurante.restaurante.repositories.OrderRepository;
import com.restaurante.restaurante.services.IClientService;
import com.restaurante.restaurante.utils.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service

public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ClientMapper clientMapper;


    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                             OrderRepository orderRepository,
                             ClientMapper clientMapper
                            ) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.clientMapper = clientMapper;

    }

    @Override
    public ClientDTO addClient(ClientDTO clientDTO) {
        Client clientEntity = clientMapper.toEntity(clientDTO);
        validateAndSetDefaults(clientEntity);
        Client savedClient = clientRepository.save(clientEntity);
        return clientMapper.toDTO(savedClient);
    }

    @Override
    public Optional<ClientDTO> getClient(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toDTO);
    }

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        return clientRepository.findById(id)
                .map(client -> {
                    // Actualizar la información básica del cliente
                    client.setName(clientDTO.getName());
                    client.setEmail(clientDTO.getEmail());
                    Client updatedClient = clientRepository.save(client);
                    validateAndSetDefaults(updatedClient);
                    return clientMapper.toDTO(updatedClient);
                })
                .orElseThrow(() -> new RuntimeException("El cliente con el id " + id + " no pudo ser actualizado"));
    }

    @Override

    public String checkAndUpdateClientStatus(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        long orderCount = orderRepository.countByClientId(clientId);

        // Si el cliente tiene más de 10 órdenes, cambiar el tipo de usuario
        if (orderCount >= 10 && UserType.REGULAR.toString().equals(client.getUserType())) {
            client.setUserType(UserType.FREQUENT.toString());

            clientRepository.save(client);
        }
        return client.getUserType();
    }

    @Override
    public List<Orders> getOrdersByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    public void validateAndSetDefaults(Client client) {

            client.setUserType(UserType.REGULAR.toString());
        if (client.getOrders() != null) {
            client.getOrders().forEach(order -> {
                if (order.getTotalPrice() == null && order.getDishes() != null) {
                    Double totalPrice = (double) order.getDishes().stream()
                            .mapToInt(dish -> dish.getPrice().intValue())
                            .sum();
                    order.setTotalPrice(totalPrice);
                }
            });
        }
    }
}