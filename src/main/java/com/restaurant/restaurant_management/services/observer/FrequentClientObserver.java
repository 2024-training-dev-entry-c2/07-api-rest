package com.restaurant.restaurant_management.services.observer;

import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.repositories.ClientRepository;

public class FrequentClientObserver implements IObserver {
  private final ClientRepository clientRepository; // JPA repository

  public FrequentClientObserver(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public void update(String eventType, Object data) {
    if (eventType.equals("NewOrder")) {
      Client client = (Client) data;
      long orderCount = client.getOrders().size();
      if (orderCount >= 10 && Boolean.TRUE.equals(!client.getIsFrequent())) {
        client.setIsFrequent(true);
        clientRepository.save(client);
      }
    }
  }
}
