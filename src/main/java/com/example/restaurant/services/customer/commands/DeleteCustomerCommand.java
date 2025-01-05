package com.example.restaurant.services.customer.commands;

import com.example.restaurant.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteCustomerCommand {

  private final CustomerRepository customerRepository;

  public DeleteCustomerCommand(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public void execute(Long customerId) {
    if (!customerRepository.existsById(customerId)) {
      throw new IllegalArgumentException("No se encontró cliente con ID: " + customerId);
    }
    customerRepository.deleteById(customerId);
  }
}
