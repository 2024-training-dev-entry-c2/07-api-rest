package com.example.restaurant.services.customer.commands;

import com.example.restaurant.models.Customer;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListCustomersCommandTest {

    private final CustomerRepository customerRepository = mock(CustomerRepository.class);

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
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John");
        customer1.setLastName("Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setPhone("123-456-7890");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane");
        customer2.setLastName("Smith");
        customer2.setEmail("jane.smith@example.com");
        customer2.setPhone("098-765-4321");

        return List.of(customer1, customer2);
    }
}