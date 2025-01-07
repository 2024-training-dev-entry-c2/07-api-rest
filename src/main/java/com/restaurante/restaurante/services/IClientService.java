package com.restaurante.restaurante.services;

import com.restaurante.restaurante.dto.ClientDTO;
import com.restaurante.restaurante.models.Orders;

import java.util.List;
import java.util.Optional;

public interface IClientService {
    ClientDTO addClient(ClientDTO clientDTO);
    Optional<ClientDTO> getClient(Long id);
    List<ClientDTO> getClients();
    void deleteClient(Long id);
    ClientDTO updateClient(Long id, ClientDTO clientDTO);
    String checkAndUpdateClientStatus(Long clientId);
    List<Orders> getOrdersByClientId(Long clientId);
}
