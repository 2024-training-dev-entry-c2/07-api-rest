package com.example.restaurant.services.customer.commands;

import com.example.restaurant.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCustomerCommand {

  private final CustomerRepository customerRepository;

  public void execute(Long customerId) {
    if (!customerRepository.existsById(customerId)) {
      throw new IllegalArgumentException("No se encontró cliente con ID: " + customerId);
    }
    customerRepository.deleteById(customerId);
  }
}
