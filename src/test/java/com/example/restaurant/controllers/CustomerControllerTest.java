package com.example.restaurant.controllers;

import com.example.restaurant.models.dto.customer.CustomerRequestDTO;
import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.services.customer.CustomerCommandHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CustomerControllerTest {

  private final CustomerCommandHandler customerService;
  private final WebTestClient webTestClient;

  CustomerControllerTest() {
    customerService = mock(CustomerCommandHandler.class);
    this.webTestClient = WebTestClient.bindToController(new CustomerController(customerService)).build();
  }

  @Test
  @DisplayName("Crear un cliente")
  void createCustomer() {

    CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
    customerRequestDTO.setName("John");
    customerRequestDTO.setLastName("Doe");
    customerRequestDTO.setEmail("doe@example.com");
    customerRequestDTO.setPhone("+1 223-3343-332");

    CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
    customerResponseDTO.setName("John");
    customerResponseDTO.setLastName("Doe");
    customerResponseDTO.setEmail("doe@example.com");
    customerResponseDTO.setPhone("+1 223-3343-332");

    when(customerService.createCustomer(any(CustomerRequestDTO.class))).thenReturn(customerResponseDTO);

    webTestClient
            .post()
            .uri("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(customerRequestDTO)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(CustomerResponseDTO.class)
            .value(d -> {
              assertEquals(customerResponseDTO.getName(), d.getName());
              assertEquals(customerResponseDTO.getLastName(), d.getLastName());
              assertEquals(customerResponseDTO.getEmail(), d.getEmail());
              assertEquals(customerResponseDTO.getPhone(), d.getPhone());
            });

    Mockito.verify(customerService).createCustomer(any(CustomerRequestDTO.class));
  }

  @Test
  @DisplayName("Actualizar un cliente")
  void updateCustomer() {

    CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
    customerRequestDTO.setName("John");
    customerRequestDTO.setLastName("Doe");
    customerRequestDTO.setEmail("doe@example.com");
    customerRequestDTO.setPhone("+1 223-3343-332");

    CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
    customerResponseDTO.setName("John");
    customerResponseDTO.setLastName("Doe");
    customerResponseDTO.setEmail("doe@example.com");
    customerResponseDTO.setPhone("+1 223-3343-332");

    when(customerService.updateCustomer(any(Long.class), any(CustomerRequestDTO.class))).thenReturn(customerResponseDTO);

    webTestClient
            .put()
            .uri("/customers/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(customerRequestDTO)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(CustomerResponseDTO.class)
            .value(d -> {
              assertEquals(customerResponseDTO.getName(), d.getName());
              assertEquals(customerResponseDTO.getLastName(), d.getLastName());
              assertEquals(customerResponseDTO.getEmail(), d.getEmail());
              assertEquals(customerResponseDTO.getPhone(), d.getPhone());
            });

    Mockito.verify(customerService).updateCustomer(any(Long.class), any(CustomerRequestDTO.class));
  }

  @Test
  @DisplayName("Eliminar un cliente")
  void deleteCustomer() {

    doNothing().when(customerService).deleteCustomer(any(Long.class));

    webTestClient
            .delete()
            .uri("/customers/{id}", 1L)
            .exchange()
            .expectStatus().isNoContent();

    Mockito.verify(customerService).deleteCustomer(any(Long.class));
  }

  @Test
  @DisplayName("Obtener un cliente por ID")
  void getCustomerById() {

    CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
    customerResponseDTO.setCustomerId(1L);
    customerResponseDTO.setName("John");
    customerResponseDTO.setLastName("Doe");
    customerResponseDTO.setEmail("doe@example.com");
    customerResponseDTO.setPhone("+1 223-3343-332");

    when(customerService.getCustomerById(any(Long.class))).thenReturn(customerResponseDTO);

    webTestClient
            .get()
            .uri("/customers/{id}", 1L)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(CustomerResponseDTO.class)
            .value(d -> {
              assertEquals(customerResponseDTO.getCustomerId(), d.getCustomerId());
              assertEquals(customerResponseDTO.getName(), d.getName());
              assertEquals(customerResponseDTO.getLastName(), d.getLastName());
              assertEquals(customerResponseDTO.getEmail(), d.getEmail());
              assertEquals(customerResponseDTO.getPhone(), d.getPhone());
            });

    Mockito.verify(customerService).getCustomerById(any(Long.class));
  }

  @Test
  @DisplayName("Listar todos los clientes")
  void listCustomers() {

    when(customerService.listCustomers()).thenReturn(getCustomerList());

    webTestClient
            .get()
            .uri("/customers")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(CustomerResponseDTO.class)
            .value(d -> {
              assertEquals(getCustomerList().size(), d.size());
            });

    Mockito.verify(customerService).listCustomers();
  }

  public List<CustomerResponseDTO> getCustomerList() {

    CustomerResponseDTO customer1 = new CustomerResponseDTO();
    customer1.setCustomerId(1L);
    customer1.setName("John");
    customer1.setLastName("Doe");
    customer1.setEmail("doe@example.com");
    customer1.setPhone("+1 223-3343-332");

    CustomerResponseDTO customer2 = new CustomerResponseDTO();
    customer2.setCustomerId(2L);
    customer2.setName("Jane");
    customer2.setLastName("Doe");
    customer2.setEmail("doe@gmail.com");
    customer2.setPhone("+57 313-221-12-34");

    return List.of(customer1, customer2);
  }
}