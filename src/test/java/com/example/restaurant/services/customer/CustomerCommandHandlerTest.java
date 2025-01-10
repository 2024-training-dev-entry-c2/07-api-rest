package com.example.restaurant.services.customer;

import com.example.restaurant.models.dto.customer.CustomerRequestDTO;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.services.customer.commands.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CustomerCommandHandlerTest {

    @Mock
    private CreateCustomerCommand createCustomerCommand;

    @Mock
    private UpdateCustomerCommand updateCustomerCommand;

    @Mock
    private DeleteCustomerCommand deleteCustomerCommand;

    @Mock
    private GetCustomerByIdCommand getCustomerByIdCommand;

    @Mock
    private ListCustomersCommand listCustomersCommand;

    @InjectMocks
    private CustomerCommandHandler customerCommandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test de creación de un cliente")
    void createCustomer() {
        CustomerRequestDTO requestDTO = new CustomerRequestDTO();
        CustomerResponseDTO responseDTO = new CustomerResponseDTO();

        when(createCustomerCommand.execute(any(CustomerRequestDTO.class))).thenReturn(responseDTO);

        CustomerResponseDTO result = customerCommandHandler.createCustomer(requestDTO);

        assertEquals(responseDTO, result);
        verify(createCustomerCommand).execute(requestDTO);
    }

    @Test
    @DisplayName("Test de actualización de un cliente")
    void updateCustomer() {
        Long customerId = 1L;
        CustomerRequestDTO requestDTO = new CustomerRequestDTO();
        CustomerResponseDTO responseDTO = new CustomerResponseDTO();

        when(updateCustomerCommand.execute(anyLong(), any(CustomerRequestDTO.class))).thenReturn(responseDTO);

        CustomerResponseDTO result = customerCommandHandler.updateCustomer(customerId, requestDTO);

        assertEquals(responseDTO, result);
        verify(updateCustomerCommand).execute(customerId, requestDTO);
    }

    @Test
    @DisplayName("Test de eliminación de un cliente")
    void deleteCustomer() {
        Long customerId = 1L;

        doNothing().when(deleteCustomerCommand).execute(anyLong());

        customerCommandHandler.deleteCustomer(customerId);

        verify(deleteCustomerCommand).execute(customerId);
    }

    @Test
    @DisplayName("Test de obtención de un cliente por ID")
    void getCustomerById() {
        Long customerId = 1L;
        CustomerResponseDTO responseDTO = new CustomerResponseDTO();

        when(getCustomerByIdCommand.execute(anyLong())).thenReturn(responseDTO);

        CustomerResponseDTO result = customerCommandHandler.getCustomerById(customerId);

        assertEquals(responseDTO, result);
        verify(getCustomerByIdCommand).execute(customerId);
    }

    @Test
    @DisplayName("Test de listado de clientes")
    void listCustomers() {
        List<CustomerResponseDTO> responseDTOList = List.of(new CustomerResponseDTO());

        when(listCustomersCommand.execute()).thenReturn(responseDTOList);

        List<CustomerResponseDTO> result = customerCommandHandler.listCustomers();

        assertEquals(responseDTOList, result);
        verify(listCustomersCommand).execute();
    }
}