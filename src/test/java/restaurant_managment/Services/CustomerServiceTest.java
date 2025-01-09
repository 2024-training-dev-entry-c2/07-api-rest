package restaurant_managment.Services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import restaurant_managment.Models.CustomerModel;
import restaurant_managment.Repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

  private final CustomerRepository customerRepository = mock(CustomerRepository.class);
  private final CustomerService customerService = new CustomerService();

  public CustomerServiceTest() {
    customerService.customerRepository = customerRepository;
  }

  @Test
  @DisplayName("Get all customers")
  void getAllCustomers() {
    CustomerModel customer1 = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    CustomerModel customer2 = new CustomerModel(2L, true, "Jane", "Doe", "jane.doe@example.com", "0987654321");
    List<CustomerModel> customers = List.of(customer1, customer2);

    when(customerRepository.findAll()).thenReturn(customers);

    List<CustomerModel> result = customerService.getAllCustomers();
    assertEquals(2, result.size());
    assertEquals(customers, result);

    verify(customerRepository).findAll();
  }

  @Test
  @DisplayName("Get customer by ID")
  void getCustomerById() {
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");

    when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

    Optional<CustomerModel> result = customerService.getCustomerById(1L);
    assertEquals(Optional.of(customer), result);

    verify(customerRepository).findById(anyLong());
  }

  @Test
  @DisplayName("Create customer")
  void createCustomer() {
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");

    when(customerRepository.save(any(CustomerModel.class))).thenReturn(customer);

    CustomerModel result = customerService.createCustomer(customer);
    assertEquals(customer, result);

    verify(customerRepository).save(any(CustomerModel.class));
  }

  @Test
  @DisplayName("Update customer")
  void updateCustomer() {
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    CustomerModel updatedCustomer = new CustomerModel(1L, true, "Johnny", "Doe", "johnny.doe@example.com", "0987654321");

    when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
    when(customerRepository.save(any(CustomerModel.class))).thenReturn(updatedCustomer);

    CustomerModel result = customerService.updateCustomer(1L, updatedCustomer);
    assertEquals(updatedCustomer, result);

    verify(customerRepository).findById(anyLong());
    verify(customerRepository).save(any(CustomerModel.class));
  }

  @Test
  @DisplayName("Update customer - not found")
  void updateCustomerNotFound() {
    CustomerModel updatedCustomer = new CustomerModel(1L, true, "Johnny", "Doe", "johnny.doe@example.com", "0987654321");

    when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      customerService.updateCustomer(1L, updatedCustomer);
    });

    assertEquals("Customer not found", exception.getMessage());

    verify(customerRepository).findById(anyLong());
    verify(customerRepository, never()).save(any(CustomerModel.class));
  }

  @Test
  @DisplayName("Delete customer")
  void deleteCustomer() {
    doNothing().when(customerRepository).deleteById(anyLong());

    customerService.deleteCustomer(1L);

    verify(customerRepository).deleteById(anyLong());
  }
}