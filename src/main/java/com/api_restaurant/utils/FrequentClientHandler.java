package com.api_restaurant.utils;

import com.api_restaurant.models.Client;
import com.api_restaurant.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FrequentClientHandler implements ClientHandler {
    private ClientHandler nextClientHandler;
    private final OrderRepository orderRepository;

    @Autowired
    public FrequentClientHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void setNext(ClientHandler clientHandler) {
        this.nextClientHandler = clientHandler;
    }

    @Override
    public void handle(Client client) {
        long orderCount = orderRepository.countByClientId(client.getId());
        if (orderCount >= 10) {
            client.setFrequent(true);
        } else {
            client.setFrequent(false);
        }
        if (nextClientHandler != null) {
            nextClientHandler.handle(client);
        }
    }
}