package com.example.restaurant.services.customer.commands;

import com.example.restaurant.mappers.CustomerMapper;
import com.example.restaurant.models.Customer;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetCustomerByIdCommand {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public CustomerResponseDTO execute(Long customerId) {
    Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
    if (optionalCustomer.isEmpty()) {
      throw new RuntimeException("No se encontró cliente con ID: " + customerId);
    }
    return customerMapper.toDTO(optionalCustomer.get());
  }
}
