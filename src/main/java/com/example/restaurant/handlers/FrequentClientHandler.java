package com.example.restaurant.handlers;

import com.example.restaurant.handlers.interfaces.IOrderHandler;
import com.example.restaurant.models.Order;
import com.example.restaurant.services.client.IsFrequentClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FrequentClientHandler implements IOrderHandler {

    private final IsFrequentClientService isFrequentClientService;

    @Autowired
    public FrequentClientHandler(IsFrequentClientService isFrequentClientService) {
        this.isFrequentClientService = isFrequentClientService;
    }

    @Override
    public void handle(Order order) {
        isFrequentClientService.execute(order.getClient());
    }
}
