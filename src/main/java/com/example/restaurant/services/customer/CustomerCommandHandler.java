package com.example.restaurant.services.customer;

import com.example.restaurant.models.dto.CustomerDTO;
import com.example.restaurant.services.customer.commands.CreateCustomerCommand;
import com.example.restaurant.services.customer.commands.DeleteCustomerCommand;
import com.example.restaurant.services.customer.commands.GetCustomerByIdCommand;
import com.example.restaurant.services.customer.commands.ListCustomersCommand;
import com.example.restaurant.services.customer.commands.UpdateCustomerCommand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerCommandHandler {

  private final CreateCustomerCommand createCustomerCommand;
  private final UpdateCustomerCommand updateCustomerCommand;
  private final DeleteCustomerCommand deleteCustomerCommand;
  private final GetCustomerByIdCommand getCustomerByIdCommand;
  private final ListCustomersCommand listCustomersCommand;

  public CustomerCommandHandler(
          CreateCustomerCommand createCustomerCommand,
          UpdateCustomerCommand updateCustomerCommand,
          DeleteCustomerCommand deleteCustomerCommand,
          GetCustomerByIdCommand getCustomerByIdCommand,
          ListCustomersCommand listCustomersCommand
  ) {
    this.createCustomerCommand = createCustomerCommand;
    this.updateCustomerCommand = updateCustomerCommand;
    this.deleteCustomerCommand = deleteCustomerCommand;
    this.getCustomerByIdCommand = getCustomerByIdCommand;
    this.listCustomersCommand = listCustomersCommand;
  }

  public CustomerDTO createCustomer(CustomerDTO customerDTO) {
    return createCustomerCommand.execute(customerDTO);
  }

  public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
    return updateCustomerCommand.execute(customerDTO);
  }

  public void deleteCustomer(Long customerId) {
    deleteCustomerCommand.execute(customerId);
  }

  public CustomerDTO getCustomerById(Long customerId) {
    return getCustomerByIdCommand.execute(customerId);
  }

  public List<CustomerDTO> listCustomers() {
    return listCustomersCommand.execute();
  }
}

