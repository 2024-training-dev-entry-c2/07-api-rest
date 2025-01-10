package com.example.restaurant.services.customer.commands;

import com.example.restaurant.mappers.CustomerMapper;
import com.example.restaurant.models.Customer;
import com.example.restaurant.models.dto.customer.CustomerRequestDTO;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateCustomerCommandTest {

    private final CustomerRepository customerRepository = mock(CustomerRepository.class);
    private final CustomerMapper customerMapper = mock(CustomerMapper.class);


    @InjectMocks
    private CreateCustomerCommand createCustomerCommand;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Crear un cliente")
    void execute() {

        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
        customerRequestDTO.setName("John");
        customerRequestDTO.setLastName("Doe");
        customerRequestDTO.setEmail("johndoemaster@gmail.com");
        customerRequestDTO.setPhone("+1 223-3343-332");

        Customer customer = new Customer();
        customer.setName("John");
        customer.setLastName("Doe");
        customer.setEmail("johndoemaster@gmail.com");
        customer.setPhone("+1 223-3343-332");

        Customer savedCustomer = new Customer();
        savedCustomer.setCustomerId(1L);
        savedCustomer.setName("John");
        savedCustomer.setLastName("Doe");
        savedCustomer.setEmail("johndoemaster@gmail.com");
        savedCustomer.setPhone("+1 223-3343-332");

        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setCustomerId(1L);
        customerResponseDTO.setName("John");
        customerResponseDTO.setLastName("Doe");
        customerResponseDTO.setEmail("johndoemaster@gmail.com");
        customerResponseDTO.setPhone("+1 223-3343-332");


        when(customerMapper.toEntity(any(CustomerRequestDTO.class))).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(customerMapper.toDTO(any(Customer.class))).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = createCustomerCommand.execute(customerRequestDTO);

        assertEquals(customerResponseDTO, result);

        Mockito.verify(customerMapper).toEntity(customerRequestDTO);
        Mockito.verify(customerRepository).save(customer);
    }
}