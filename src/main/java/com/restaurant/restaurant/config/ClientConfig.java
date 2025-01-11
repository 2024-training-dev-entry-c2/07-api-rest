package com.restaurant.restaurant.config;

import com.restaurant.restaurant.observer.observers.ClientTypeNotificationObserver;
import com.restaurant.restaurant.services.ClientService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
  @Autowired
  private ClientService clientService;

  @Autowired
  public ClientTypeNotificationObserver clientTypeNotificationObserver;

  @PostConstruct
  public void setup(){
    clientService.addObserver(clientTypeNotificationObserver);
  }
}
