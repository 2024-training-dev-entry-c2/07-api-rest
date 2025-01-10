package com.example.restaurant.services.customer.commands;

import com.example.restaurant.mappers.CustomerMapper;
import com.example.restaurant.models.Customer;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class GetCustomerByIdCommandTest {

    private final CustomerRepository customerRepository = mock(CustomerRepository.class);
    private final CustomerMapper customerMapper = mock(CustomerMapper.class);

    @InjectMocks
    private GetCustomerByIdCommand getCustomerByIdCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test de servicio para obtener un cliente por ID")
    void execute() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("John Doe");

        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(customerId);
        customerResponseDTO.setName("John Doe");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = getCustomerByIdCommand.execute(customerId);

        assertEquals(customerResponseDTO, result);

        verify(customerRepository).findById(customerId);
    }

    @Test
    @DisplayName("Test de servicio para obtener un cliente por ID no encontrado")
    void executeCustomerNotFound() {
        Long customerId = 1L;

        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> getCustomerByIdCommand.execute(customerId));

        verify(customerRepository).findById(customerId);
    }
}