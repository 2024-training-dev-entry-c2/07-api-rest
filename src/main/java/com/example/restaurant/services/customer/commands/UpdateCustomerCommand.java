package com.example.restaurant.services.customer.commands;

import com.example.restaurant.models.dto.CustomerDTO;
import com.example.restaurant.mapper.CustomerMapper;
import com.example.restaurant.models.Customer;
import com.example.restaurant.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateCustomerCommand {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public UpdateCustomerCommand(CustomerRepository customerRepository, CustomerMapper customerMapper) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  public CustomerDTO execute(CustomerDTO customerDTO) {
    Optional<Customer> optionalCustomer = customerRepository.findById(customerDTO.getId());
    if (optionalCustomer.isEmpty()) {
      throw new IllegalArgumentException("No se encontró cliente con ID: " + customerDTO.getId());
    }
    Customer customerToUpdate = optionalCustomer.get();
    customerToUpdate.setName(customerDTO.getName());
    customerToUpdate.setLastName(customerDTO.getLastName());
    customerToUpdate.setEmail(customerDTO.getEmail());
    customerToUpdate.setPhone(customerDTO.getPhone());
    Customer updatedCustomer = customerRepository.save(customerToUpdate);
    return customerMapper.toDTO(updatedCustomer);
  }
}
