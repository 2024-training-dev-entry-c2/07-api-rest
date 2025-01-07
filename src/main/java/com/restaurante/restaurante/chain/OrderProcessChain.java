package com.restaurante.restaurante.chain;

import com.restaurante.restaurante.models.Orders;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessChain {
    private final Handler chain;

    public OrderProcessChain(ClientStatusHandler clientStatusHandler,
                             DishPopularityHandler dishPopularityHandler) {
        this.chain = clientStatusHandler;
        clientStatusHandler.setNext(dishPopularityHandler);
    }

    public void processOrder(Orders order) {
        if (order != null && order.getClient() != null) {
            chain.process(order);
        }
    }
}