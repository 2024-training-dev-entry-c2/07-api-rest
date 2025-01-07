package com.example.restaurant.services.customer;

import com.example.restaurant.models.dto.customer.CustomerRequestDTO;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.services.customer.commands.CreateCustomerCommand;
import com.example.restaurant.services.customer.commands.DeleteCustomerCommand;
import com.example.restaurant.services.customer.commands.GetCustomerByIdCommand;
import com.example.restaurant.services.customer.commands.ListCustomersCommand;
import com.example.restaurant.services.customer.commands.UpdateCustomerCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//Recordar que es una anotacion de lombok para inyectar todas las dependencias necesarias por constructor
public class CustomerCommandHandler {

  private final CreateCustomerCommand createCustomerCommand;
  private final UpdateCustomerCommand updateCustomerCommand;
  private final DeleteCustomerCommand deleteCustomerCommand;
  private final GetCustomerByIdCommand getCustomerByIdCommand;
  private final ListCustomersCommand listCustomersCommand;

  public CustomerResponseDTO createCustomer(CustomerRequestDTO customerDTO) {
    return createCustomerCommand.execute(customerDTO);
  }

  public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customerDTO) {
    return updateCustomerCommand.execute(id, customerDTO);
  }

  public void deleteCustomer(Long customerId) {
    deleteCustomerCommand.execute(customerId);
  }

  public CustomerResponseDTO getCustomerById(Long customerId) {
    return getCustomerByIdCommand.execute(customerId);
  }

  public List<CustomerResponseDTO> listCustomers() {
    return listCustomersCommand.execute();
  }
}

