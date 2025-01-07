package com.example.restaurant.services.customer.commands;

import com.example.restaurant.mappers.CustomerMapper;
import com.example.restaurant.models.Customer;
import com.example.restaurant.models.dto.customer.CustomerRequestDTO;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateCustomerCommand {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public CustomerResponseDTO execute(Long id, CustomerRequestDTO customerDTO) {
    Optional<Customer> optionalCustomer = customerRepository.findById(id);
    if (optionalCustomer.isEmpty()) {
      throw new IllegalArgumentException("No se encontró cliente con ID: " + id);
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
