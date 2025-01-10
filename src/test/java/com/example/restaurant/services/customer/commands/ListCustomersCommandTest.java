package com.example.restaurant.services.customer.commands;

import com.example.restaurant.mappers.CustomerMapper;
import com.example.restaurant.models.Customer;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListCustomersCommandTest {

  private final CustomerRepository customerRepository = mock(CustomerRepository.class);

  @Mock
  private CustomerMapper customerMapper;

  @InjectMocks
  private ListCustomersCommand listCustomersCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Listar todos los clientes")
  void execute() {

    when(customerRepository.findAll()).thenReturn(getCustomersList());
    List<CustomerResponseDTO> customers = listCustomersCommand.execute();

    assertNotNull(customers);
    assertEquals(2, customers.size());

  }

  public List<Customer> getCustomersList() {
    Customer customer1 = new Customer(
            1L,
            "John",
            "Doe",
            "john.doe@example.com",
            "123-456-7890"
    );

    Customer customer2 = new Customer(
            2L,
            "Jane",
            "Smith",
            "jane.smith@example.com",
            "098-765-4321"
    );

    return List.of(customer1, customer2);
  }
}