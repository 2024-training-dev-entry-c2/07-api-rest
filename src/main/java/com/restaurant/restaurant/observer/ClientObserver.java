package com.restaurant.restaurant.observer;

import com.restaurant.restaurant.models.ClientModel;
import com.restaurant.restaurant.repositories.OrderRepository;

public class ClientObserver implements Observer<ClientModel> {
  private final OrderRepository orderRepository;

  public ClientObserver(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public void update(ClientModel client) {
    Integer orderCount = orderRepository.countOrdersByClientId(client.getId());
    if(orderCount >= 10 && !client.getIsFrecuent()){
      client.setIsFrecuent(true);
      System.out.println("Client promoted to frequent: " + client.getName());
    }
  }
}
