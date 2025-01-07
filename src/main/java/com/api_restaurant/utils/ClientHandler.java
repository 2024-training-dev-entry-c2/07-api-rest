package com.api_restaurant.utils;

import com.api_restaurant.models.Client;

public interface ClientHandler {
    void setNext(ClientHandler clientHandler);
    void handle(Client client);
}