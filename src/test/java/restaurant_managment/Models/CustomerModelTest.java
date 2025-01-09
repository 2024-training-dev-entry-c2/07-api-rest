package restaurant_managment.Models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class CustomerModelTest {

  private CustomerModel customer;
  private EntityManager mockEntityManager;
  private TypedQuery<Long> mockQuery;

  @BeforeEach
  public void setUp() {
    customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    mockEntityManager = mock(EntityManager.class);
    mockQuery = mock(TypedQuery.class);
  }

  @Test
  public void testUpdateFrecuency_NotFrequent() {
    when(mockEntityManager.createQuery("SELECT COUNT(o) FROM OrderModel o WHERE o.reservation.customer.id = :customerId", Long.class))
      .thenReturn(mockQuery);
    when(mockQuery.setParameter("customerId", customer.getId())).thenReturn(mockQuery);
    when(mockQuery.getSingleResult()).thenReturn(10L);

    customer.updateFrecuency(mockEntityManager);

    assertTrue(customer.getIsFrequent());
  }

  @Test
  public void testUpdateFrecuency_AlreadyFrequent() {

    customer.setIsFrequent(true);

    customer.updateFrecuency(mockEntityManager);

    verify(mockEntityManager, never()).createQuery(anyString(), eq(Long.class));
  }

  @Test
  public void testUpdateFrecuency_NotFrequent_LessThan10Orders() {

    when(mockEntityManager.createQuery("SELECT COUNT(o) FROM OrderModel o WHERE o.reservation.customer.id = :customerId", Long.class))
      .thenReturn(mockQuery);
    when(mockQuery.setParameter("customerId", customer.getId())).thenReturn(mockQuery);
    when(mockQuery.getSingleResult()).thenReturn(5L);

    customer.updateFrecuency(mockEntityManager);

    assertFalse(customer.getIsFrequent());
  }

  @Test
  public void testVerifyTotalOrders_Popular() {

    customer.verifyTotalOrders(10);

    assertTrue(customer.getIsFrequent());
  }

  @Test
  public void testVerifyTotalOrders_NotPopular() {

    customer.verifyTotalOrders(5);

    assertFalse(customer.getIsFrequent());
  }

  @Test
  public void testConstructorWithAllFields() {

    CustomerModel fullCustomer = new CustomerModel(2L, true, "Jane", "Doe", "jane.doe@example.com", "0987654321");

    assertTrue(fullCustomer.getIsFrequent());
    assertEquals("Jane", fullCustomer.getFirstName());
    assertEquals("Doe", fullCustomer.getLastName());
    assertEquals("jane.doe@example.com", fullCustomer.getEmail());
    assertEquals("0987654321", fullCustomer.getPhone());
  }

  @Test
  public void testConstructorWithoutId() {

    CustomerModel partialCustomer = new CustomerModel(true, "Alice", "Smith", "alice.smith@example.com", "1122334455");

    assertTrue(partialCustomer.getIsFrequent());
    assertEquals("Alice", partialCustomer.getFirstName());
    assertEquals("Smith", partialCustomer.getLastName());
    assertEquals("alice.smith@example.com", partialCustomer.getEmail());
    assertEquals("1122334455", partialCustomer.getPhone());
  }

  @Test
  public void testNoArgConstructor() {

    CustomerModel emptyCustomer = new CustomerModel();

    assertFalse(emptyCustomer.getIsFrequent());
    assertNull(emptyCustomer.getFirstName());
    assertNull(emptyCustomer.getLastName());
    assertNull(emptyCustomer.getEmail());
    assertNull(emptyCustomer.getPhone());
  }
}