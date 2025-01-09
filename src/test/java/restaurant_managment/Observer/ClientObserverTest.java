package restaurant_managment.Observer;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import restaurant_managment.Models.CustomerModel;

import static org.mockito.Mockito.*;

public class ClientObserverTest {

  private ClientObserver clientObserver;
  private CustomerModel mockCustomer;
  private EntityManager mockEntityManager;

  @BeforeEach
  public void setUp() {
    mockCustomer = mock(CustomerModel.class);
    mockEntityManager = mock(EntityManager.class);
    clientObserver = new ClientObserver(mockCustomer, mockEntityManager);
  }

  @Test
  public void testUpdate_CustomerIsFrequent() {

    when(mockCustomer.getIsFrequent()).thenReturn(true);

    clientObserver.update();

    verify(mockCustomer).updateFrecuency(mockEntityManager);
    verify(mockCustomer).getIsFrequent();
    verify(mockCustomer).getFirstName();
    verify(mockCustomer).getLastName();
    System.out.println("El cliente " + mockCustomer.getFirstName() + " " + mockCustomer.getLastName() + " es cliente frecuente!!");
  }

  @Test
  public void testUpdate_CustomerIsNotFrequent() {

    when(mockCustomer.getIsFrequent()).thenReturn(false);

    clientObserver.update();

    verify(mockCustomer).updateFrecuency(mockEntityManager);
    verify(mockCustomer).getIsFrequent();
    verify(mockCustomer, never()).getFirstName();
    verify(mockCustomer, never()).getLastName();
  }
}