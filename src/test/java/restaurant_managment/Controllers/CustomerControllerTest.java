package restaurant_managment.Controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import restaurant_managment.Models.CustomerModel;
import restaurant_managment.Proxy.CustomerServiceProxy;
import restaurant_managment.Services.CustomerService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

  private final WebTestClient webTestClient;
  private final CustomerService customerService;
  private final CustomerServiceProxy customerServiceProxy;

  public CustomerControllerTest() {
    customerServiceProxy = mock(CustomerServiceProxy.class);
    customerService = mock(CustomerService.class);
    webTestClient = WebTestClient.bindToController(new CustomerController(customerService, customerServiceProxy)).build();
  }

  @Test
  @DisplayName("Create customer")
  void createCustomer() {
    CustomerModel customer = new CustomerModel(1L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890");
    when(customerService.createCustomer(any(CustomerModel.class))).thenReturn(customer);

    webTestClient.post()
      .uri("/customers")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(customer)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(CustomerModel.class)
      .value(customer1 -> {
        assertEquals(customer.getFirstName(), customer1.getFirstName());
        assertEquals(customer.getLastName(), customer1.getLastName());
        assertEquals(customer.getId(), customer1.getId());
        assertEquals(customer.getEmail(), customer1.getEmail());
        assertEquals(customer.getPhone(), customer1.getPhone());
      });
    Mockito.verify(customerService).createCustomer(any(CustomerModel.class));
  }

  @Test
  @DisplayName("Get customer by id")
  void getCustomerById() {
    CustomerModel customer = new CustomerModel(1L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890");
    when(customerServiceProxy.getCustomerById(any(Long.class))).thenReturn(Optional.of(customer));

    webTestClient.get()
      .uri("/customers/{id}", 1L)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(CustomerModel.class)
      .value(customer1 -> {
        assertEquals(customer.getFirstName(), customer1.getFirstName());
        assertEquals(customer.getLastName(), customer1.getLastName());
        assertEquals(customer.getId(), customer1.getId());
        assertEquals(customer.getEmail(), customer1.getEmail());
        assertEquals(customer.getPhone(), customer1.getPhone());
      });
    Mockito.verify(customerServiceProxy).getCustomerById(any(Long.class));
  }

  @Test
  @DisplayName("Get all customers")
  void listCustomers() {
    when(customerService.getAllCustomers()).thenReturn(getAllCustomers());

    webTestClient.get()
      .uri("/customers")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(CustomerModel.class)
      .hasSize(3)
      .value(customers -> {
        assertEquals(1L, customers.get(0).getId());
        assertEquals(2L, customers.get(1).getId());
        assertEquals(3L, customers.get(2).getId());
      })
    ;
    Mockito.verify(customerService).getAllCustomers();
  }

  @Test
  @DisplayName("Update customer")
  void updateCustomer() {
    CustomerModel customer = new CustomerModel(1L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890");

    when(customerService.updateCustomer(anyLong(), any(CustomerModel.class))).thenReturn(customer);

    webTestClient.put()
      .uri("/customers/{id}", 1L)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(customer)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(CustomerModel.class)
      .value(customer1 -> {
        assertEquals(customer.getFirstName(), customer1.getFirstName());
        assertEquals(customer.getLastName(), customer1.getLastName());
        assertEquals(customer.getId(), customer1.getId());
        assertEquals(customer.getEmail(), customer1.getEmail());
        assertEquals(customer.getPhone(), customer1.getPhone());
      });
    Mockito.verify(customerService).updateCustomer(anyLong(), any(CustomerModel.class));
  }

  @Test
  @DisplayName("Delete customer")
  void deleteCustomer() {
    doNothing().when(customerService).deleteCustomer(anyLong());

    webTestClient.delete()
      .uri("/customers/{id}", 1L)
      .exchange()
      .expectStatus().isNoContent()
      .expectBody().isEmpty();
    Mockito.verify(customerService).deleteCustomer(anyLong());
  }

  private List<CustomerModel> getAllCustomers() {
   return List.of(new CustomerModel(1L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890"),new CustomerModel(2L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890")
   ,new CustomerModel(3L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890")
   );
  }
}