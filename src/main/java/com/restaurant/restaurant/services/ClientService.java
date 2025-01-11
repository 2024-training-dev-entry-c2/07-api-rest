package com.restaurant.restaurant.services;

import com.restaurant.restaurant.dtos.ClientDTO;
import com.restaurant.restaurant.exceptions.ResourceNotFoundException;
import com.restaurant.restaurant.models.ClientModel;
import com.restaurant.restaurant.enums.ClientType;
import com.restaurant.restaurant.observer.ClientTypeSubject;
import com.restaurant.restaurant.repositories.IClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService extends ClientTypeSubject {

  private final IClientRepository IClientRepository;
  private final ClientTypeSubject clientTypeSubject;

  @Transactional
  public List<ClientDTO> findAll(){
    return IClientRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Transactional
  public ClientDTO findById(Long id){
    return IClientRepository.findById(id).map(this::convertToDto).orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + id));
  }

  @Transactional
  public ClientDTO createClient(ClientDTO clientDTO){
    ClientModel clientModel = new ClientModel();
    clientModel.setName(clientDTO.getName());
    clientModel.setLastName(clientDTO.getLastName());
    clientModel.setEmail(clientDTO.getEmail());
    clientModel.setPhone(clientDTO.getPhone());
    clientModel.setType(ClientType.COMUN);

    return convertToDto(IClientRepository.save(clientModel));
  }

  @Transactional
  public ClientDTO updateClient(Long id, ClientDTO clientDTO){
    ClientModel clientModel = IClientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + id));
    clientModel.setName(clientDTO.getName());
    clientModel.setLastName(clientDTO.getLastName());
    clientModel.setPhone(clientDTO.getPhone());
    return convertToDto(IClientRepository.save(clientModel));
  }

  @Transactional
  public ClientDTO updateClientType(Long id, ClientType newType){
    ClientModel clientModel = IClientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + id));
    ClientType oldType = clientModel.getType();
    clientModel.setType(newType);
    ClientModel updatedClient = IClientRepository.save(clientModel);
    if(oldType.equals(ClientType.COMUN) && newType.equals(ClientType.FRECUENT)){
      clientTypeSubject.notifyObservers(id, oldType.toString(), newType.toString());
    }
    return convertToDto(updatedClient);
  }

  @Transactional
  public void deleteClient(Long id){
    if(!IClientRepository.existsById(id)){
      throw new ResourceNotFoundException("Client not found with id " + id);
    }
    IClientRepository.deleteById(id);
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
