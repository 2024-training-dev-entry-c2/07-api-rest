package com.restaurant.restaurant.factories;

import com.restaurant.restaurant.models.ClientModel;

public interface ClientFactory {
  ClientModel createClient(String name, String email);
}
