package com.restaurant.management.services.observer;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.Dish;
import com.restaurant.management.observer.IOrderObserver;
import com.restaurant.management.repositories.ClientRepository;
import com.restaurant.management.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientOrderObserver implements IOrderObserver {
  private final OrderRepository orderRepository;
  private final ClientRepository clientRepository;

  @Autowired
  public ClientOrderObserver(OrderRepository orderRepository, ClientRepository clientRepository) {
    this.orderRepository = orderRepository;
    this.clientRepository = clientRepository;
  }

  @Override
  public void updateOrder(Client client, Dish dish) {
    int orderCount = orderRepository.countByClient(client);
    if (orderCount >= 10 && !client.getFrequent()){
      client.setFrequent(true);
      clientRepository.save(client);
    }
  }
}
