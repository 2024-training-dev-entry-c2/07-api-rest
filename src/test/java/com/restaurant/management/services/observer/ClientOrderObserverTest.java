package com.restaurant.management.services.observer;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.Dish;
import com.restaurant.management.repositories.ClientRepository;
import com.restaurant.management.repositories.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientOrderObserverTest {
  private final OrderRepository orderRepository;
  private final ClientRepository clientRepository;
  private final ClientOrderObserver clientOrderObserver;

  ClientOrderObserverTest() {
    this.orderRepository = mock(OrderRepository.class);
    this.clientRepository = mock(ClientRepository.class);
    this.clientOrderObserver = new ClientOrderObserver(orderRepository, clientRepository);
  }

  @Test
  @DisplayName("Actualizar cliente a frecuente cuando se agrega un pedido")
  void updateOrder() {
    Client client = new Client(1L, "name", "email");
    when(orderRepository.countByClient(client)).thenReturn(10);

    clientOrderObserver.updateOrder(client, new Dish());

    assertTrue(client.getFrequent());
    verify(clientRepository).save(client);
  }

  @Test
  @DisplayName("No actualizar cliente a frecuente cuando se agrega un pedido")
  void updateOrderNotFrequent() {
    Client client = new Client(1L, "name", "email");
    when(orderRepository.countByClient(client)).thenReturn(8);

    clientOrderObserver.updateOrder(client, new Dish());

    assertFalse(client.getFrequent());
  }

  @Test
  @DisplayName("No actualizar cliente a frecuente cuando ya es frecuente")
  void updateOrderAlreadyFrequent2() {
    Client client = new Client(1L, "name", "email");
    client.setFrequent(true);
    when(orderRepository.countByClient(client)).thenReturn(15);

    clientOrderObserver.updateOrder(client, new Dish());

    assertTrue(client.getFrequent());
  }
}