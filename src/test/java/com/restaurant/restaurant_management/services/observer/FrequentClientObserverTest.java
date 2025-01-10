package com.restaurant.restaurant_management.services.observer;

import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class FrequentClientObserverTest {
  private ClientRepository clientRepository;
  private FrequentClientObserver frequentClientObserver;

  @BeforeEach
  void setUp() {
    clientRepository = mock(ClientRepository.class);
    frequentClientObserver = new FrequentClientObserver(clientRepository);
  }

  @Test
  @DisplayName("Update Client")
  void update() {
    Client client = new Client();
    client.setIsFrequent(false);
    client.setOrders(Collections.nCopies(10, null));

    frequentClientObserver.update("NewOrder", client);

    Mockito.verify(clientRepository, Mockito.times(1)).save(client);
    assert client.getIsFrequent();
  }

  @Test
  @DisplayName("Update Client - Already Frequent")
  void testNoUpdateIfAlreadyFrequent() {
    Client client = new Client();
    client.setIsFrequent(true);
    client.setOrders(Collections.nCopies(10, null));

    frequentClientObserver.update("NewOrder", client);

    Mockito.verify(clientRepository, Mockito.never()).save(any());
  }

  @Test
  @DisplayName("Update Client - Less than 10 orders")
  void testNoUpdateIfLessThan10Orders() {
    Client client = new Client();
    client.setIsFrequent(false);
    client.setOrders(Collections.nCopies(5, null));

    frequentClientObserver.update("NewOrder", client);

    Mockito.verify(clientRepository, Mockito.never()).save(any());
    assert !client.getIsFrequent();
  }

  @Test
  @DisplayName("Update Client - Different Event Type")
  void testNoActionForDifferentEventType() {
    Client client = new Client();
    client.setIsFrequent(false);
    client.setOrders(Collections.nCopies(10, null));

    frequentClientObserver.update("OtherEvent", client);

    Mockito.verify(clientRepository,  Mockito.never()).save(any());
    assert !client.getIsFrequent();
  }

}