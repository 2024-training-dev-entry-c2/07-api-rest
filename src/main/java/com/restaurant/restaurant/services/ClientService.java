package com.restaurant.restaurant.services;

import com.restaurant.restaurant.dtos.ClientDTO;
import com.restaurant.restaurant.exceptions.ResourceNotFoundException;
import com.restaurant.restaurant.models.ClientModel;
import com.restaurant.restaurant.enums.ClientType;
import com.restaurant.restaurant.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository clientRepository;

  @Transactional
  public List<ClientDTO> findAll(){
    return clientRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Transactional
  public ClientDTO findById(Long id){
    return clientRepository.findById(id).map(this::convertToDto).orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + id));
  }

  @Transactional
  public ClientDTO createClient(ClientDTO clientDTO){
    ClientModel clientModel = new ClientModel();
    clientModel.setName(clientDTO.getName());
    clientModel.setLastName(clientDTO.getLastName());
    clientModel.setEmail(clientDTO.getEmail());
    clientModel.setPhone(clientDTO.getPhone());
    clientModel.setType(ClientType.COMUN);

    return convertToDto(clientRepository.save(clientModel));
  }

  @Transactional
  public ClientDTO updateClient(Long id, ClientDTO clientDTO){
    ClientModel clientModel = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + id));
    clientModel.setName(clientDTO.getName());
    clientModel.setLastName(clientDTO.getLastName());
    clientModel.setPhone(clientDTO.getPhone());
    return convertToDto(clientRepository.save(clientModel));
  }

  @Transactional
  public void deleteClient(Long id){
    if(!clientRepository.existsById(id)){
      throw new ResourceNotFoundException("Client not found with id " + id);
    }
    clientRepository.deleteById(id);
  }

  private ClientDTO convertToDto(ClientModel clientModel) {
    ClientDTO dto = new ClientDTO();
    dto.setId(clientModel.getId());
    dto.setName(clientModel.getName());
    dto.setLastName(clientModel.getLastName());
    dto.setEmail(clientModel.getEmail());
    dto.setPhone(clientModel.getPhone());
    dto.setType(clientModel.getType());
    return dto;
  }
}
