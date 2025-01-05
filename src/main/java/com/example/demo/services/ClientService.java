package com.example.demo.services;

import com.example.demo.DTO.ClientRequestDTO;
import com.example.demo.DTO.ClientResponseDTO;
import com.example.demo.DTO.converterDTO.ClientConverter;
import com.example.demo.models.Client;
import com.example.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Crear un nuevo cliente
    public ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO) {
        Client client = ClientConverter.toEntity(clientRequestDTO);
        Client savedClient = clientRepository.save(client);
        return ClientConverter.toResponseDTO(savedClient);
    }

    // Obtener todos los clientes
    public List<ClientResponseDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientConverter::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener cliente por ID
    public ClientResponseDTO getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        return ClientConverter.toResponseDTO(client);
    }

    // Actualizar cliente
    public ClientResponseDTO updateClient(Long id, ClientRequestDTO clientRequestDTO) {
        Client existingClient = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));

        existingClient.setName(clientRequestDTO.getName());
        existingClient.setEmail(clientRequestDTO.getEmail());
        existingClient.setIsOften(clientRequestDTO.getIsOften());

        Client updatedClient = clientRepository.save(existingClient);
        return ClientConverter.toResponseDTO(updatedClient);
    }

    // Eliminar cliente
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found");
        }
        clientRepository.deleteById(id);
    }
}
