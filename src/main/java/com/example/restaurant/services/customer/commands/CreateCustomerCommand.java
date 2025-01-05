package com.example.restaurant.services.customer.commands;

import com.example.restaurant.models.dto.CustomerDTO;
import com.example.restaurant.mapper.CustomerMapper;
import com.example.restaurant.models.Customer;
import com.example.restaurant.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateCustomerCommand {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public CreateCustomerCommand(CustomerRepository customerRepository, CustomerMapper customerMapper) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  public CustomerDTO execute(CustomerDTO customerDTO) {
    Customer customer = customerMapper.toEntity(customerDTO);
    Customer savedCustomer = customerRepository.save(customer);
    return customerMapper.toDTO(savedCustomer);
  }
}
