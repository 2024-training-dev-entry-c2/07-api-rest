package com.example.restaurant.services.customer.commands;

import com.example.restaurant.mappers.CustomerMapper;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCustomersCommand {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public List<CustomerResponseDTO> execute() {
    return customerRepository.findAll().stream()
            .map(customerMapper::toDTO)
            .toList();
  }
}
