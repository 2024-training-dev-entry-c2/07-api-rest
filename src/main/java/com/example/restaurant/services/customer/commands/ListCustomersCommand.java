package com.example.restaurant.services.customer.commands;

import com.example.restaurant.mappers.CustomerMapper;
import com.example.restaurant.models.Customer;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListCustomersCommand {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public List<CustomerResponseDTO> execute() {
    List<Customer> customersEntities = customerRepository.findAll();

    return customersEntities.stream()
            .map(customer -> customerMapper.toDTO(customer)).collect(Collectors.toList());
  }
}
