package com.example.restaurant.services.customer.commands;

import com.example.restaurant.models.dto.CustomerDTO;
import com.example.restaurant.mapper.CustomerMapper;
import com.example.restaurant.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListCustomersCommand {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public ListCustomersCommand(CustomerRepository customerRepository, CustomerMapper customerMapper) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  public List<CustomerDTO> execute() {
    return customerRepository.findAll().stream()
            .map(customerMapper::toDTO)
            .collect(Collectors.toList());
  }
}
