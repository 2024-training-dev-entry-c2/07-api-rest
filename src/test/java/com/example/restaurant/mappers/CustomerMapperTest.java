package com.example.restaurant.mappers;

import com.example.restaurant.models.Customer;
import com.example.restaurant.models.dto.customer.CustomerRequestDTO;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {
  private final CustomerMapper customerMapper = new CustomerMapper();

  @Test
  @DisplayName("Convertir de RequestDTO a Entidad")
  void toEntity() {
    CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
    customerRequestDTO.setName("John");
    customerRequestDTO.setLastName("Doe");
    customerRequestDTO.setEmail("doe@example.com");
    customerRequestDTO.setPhone("+1 223-3343-332");

    Customer customer = customerMapper.toEntity(customerRequestDTO);

    assertEquals("John", customer.getName());
    assertEquals("Doe", customer.getLastName());
    assertEquals("doe@example.com", customer.getEmail());
    assertEquals("+1 223-3343-332", customer.getPhone());
  }

  @Test
  @DisplayName("Convertir de Entidad a ResponseDTO")
  void toDTO() {
    Customer customer = new Customer();
    customer.setId(1L);
    customer.setName("John");
    customer.setLastName("Doe");
    customer.setEmail("doe@example.com");
    customer.setPhone("+1 223-3343-332");
    customer.setOrders(Collections.emptyList()); // Aseguramos que la lista de órdenes no sea null

    CustomerResponseDTO responseDTO = customerMapper.toDTO(customer);

    assertEquals(1L, responseDTO.getId());
    assertEquals("John", responseDTO.getName());
    assertEquals("Doe", responseDTO.getLastName());
    assertEquals("doe@example.com", responseDTO.getEmail());
    assertEquals("+1 223-3343-332", responseDTO.getPhone());
  }
}