package com.restaurant.restaurant.factories;

import com.restaurant.restaurant.models.ClientModel;
import org.springframework.stereotype.Component;

@Component
public class ClientFactoryImpl implements ClientFactory {
  @Override
  public ClientModel createClient(String name, String email) {
    ClientModel client = new ClientModel();
    client.setName(name);
    client.setEmail(email);
    return client;
  }
}
