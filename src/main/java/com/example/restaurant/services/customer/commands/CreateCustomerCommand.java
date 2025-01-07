package com.example.restaurant.services.customer.commands;

import com.example.restaurant.mappers.CustomerMapper;
import com.example.restaurant.models.Customer;
import com.example.restaurant.models.dto.customer.CustomerRequestDTO;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCustomerCommand {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public CustomerResponseDTO execute(CustomerRequestDTO customerDTO) {
    Customer customer = customerMapper.toEntity(customerDTO);
    Customer savedCustomer = customerRepository.save(customer);
    return customerMapper.toDTO(savedCustomer);
  }
}
