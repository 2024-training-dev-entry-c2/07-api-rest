package com.example.restaurant.services.customer.commands;

import com.example.restaurant.models.dto.CustomerDTO;
import com.example.restaurant.mapper.CustomerMapper;
import com.example.restaurant.models.Customer;
import com.example.restaurant.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetCustomerByIdCommand {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public GetCustomerByIdCommand(CustomerRepository customerRepository, CustomerMapper customerMapper) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  public CustomerDTO execute(Long customerId) {
    Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
    if (optionalCustomer.isEmpty()) {
      throw new IllegalArgumentException("No se encontró cliente con ID: " + customerId);
    }
    return customerMapper.toDTO(optionalCustomer.get());
  }
}
