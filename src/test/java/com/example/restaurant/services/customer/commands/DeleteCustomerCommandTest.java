package com.example.restaurant.services.customer.commands;

import com.example.restaurant.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteCustomerCommandTest {

    private final CustomerRepository customerRepository = mock(CustomerRepository.class);

    @InjectMocks
    private DeleteCustomerCommand deleteCustomerCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test de servicio de eliminación de un cliente")
    void execute() {
        Long customerId = 1L;

        when(customerRepository.existsById(customerId)).thenReturn(true);

        deleteCustomerCommand.execute(customerId);

        verify(customerRepository).deleteById(customerId);
    }

    @Test
    @DisplayName("Test de servicio de eliminación de un cliente con ID no encontrado")
    void executeCustomerNotFound() {
        Long customerId = 1L;

        when(customerRepository.existsById(customerId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> deleteCustomerCommand.execute(customerId));

        verify(customerRepository, never()).deleteById(customerId);
    }
}