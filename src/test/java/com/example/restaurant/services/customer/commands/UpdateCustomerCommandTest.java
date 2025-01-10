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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class UpdateCustomerCommandTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private UpdateCustomerCommand updateCustomerCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test de servicio de actualización de un cliente")
    void execute() {
        // Arrange
        Long customerId = 1L;
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
        customerRequestDTO.setName("Updated Name");
        customerRequestDTO.setLastName("Updated LastName");
        customerRequestDTO.setEmail("updated@example.com");
        customerRequestDTO.setPhone("123456789");

        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(customerId);
        existingCustomer.setName("Old Name");
        existingCustomer.setLastName("Old LastName");
        existingCustomer.setEmail("old@example.com");
        existingCustomer.setPhone("987654321");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(customerId);
        updatedCustomer.setName("Updated Name");
        updatedCustomer.setLastName("Updated LastName");
        updatedCustomer.setEmail("updated@example.com");
        updatedCustomer.setPhone("123456789");

        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setCustomerId(customerId);
        customerResponseDTO.setName("Updated Name");
        customerResponseDTO.setLastName("Updated LastName");
        customerResponseDTO.setEmail("updated@example.com");
        customerResponseDTO.setPhone("123456789");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);
        when(customerMapper.toDTO(any(Customer.class))).thenReturn(customerResponseDTO);

        // Act
        CustomerResponseDTO result = updateCustomerCommand.execute(customerId, customerRequestDTO);

        // Assert
        assertEquals(customerResponseDTO, result);
    }

    @Test
    @DisplayName("Test de servicio de actualización de un cliente con ID no encontrado")
    void executeCustomerNotFound() {
        // Arrange
        Long customerId = 1L;
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
        customerRequestDTO.setName("Updated Name");
        customerRequestDTO.setLastName("Updated LastName");
        customerRequestDTO.setEmail("updated@example.com");
        customerRequestDTO.setPhone("123456789");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> updateCustomerCommand.execute(customerId, customerRequestDTO));
    }
}