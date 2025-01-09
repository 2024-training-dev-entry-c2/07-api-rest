package restaurant_managment.Proxy;

import restaurant_managment.Models.CustomerModel;
import restaurant_managment.interfaces.ICustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceProxyTest {

  @Mock
  private ICustomerService customerService;

  @InjectMocks
  private CustomerServiceProxy customerServiceProxy;

  private CustomerModel customer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    customer = new CustomerModel(1L, false, "John", "Doe", "john@example.com", "1234567890");
  }

  @Test
  void testGetCustomerById_CustomerNotInCache() {
    when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));

    Optional<CustomerModel> result = customerServiceProxy.getCustomerById(1L);

    assertTrue(result.isPresent());
    assertEquals(customer, result.get());

    verify(customerService, times(1)).getCustomerById(1L);
  }

  @Test
  void testGetCustomerById_CustomerInCache() {
    when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));
    customerServiceProxy.getCustomerById(1L);

    Optional<CustomerModel> result = customerServiceProxy.getCustomerById(1L);

    assertTrue(result.isPresent());
    assertEquals(customer, result.get());

    verify(customerService, times(1)).getCustomerById(1L);
  }

}
