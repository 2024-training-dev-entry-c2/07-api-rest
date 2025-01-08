package com.example.restaurant.observers;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Order;
import com.example.restaurant.observers.interfaces.IObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderNotificationObserver implements IObserver<Order> {

    private static final Logger logger = LoggerFactory.getLogger(OrderNotificationObserver.class);

    @Override
    public void update(EventType eventType, Order order) {
        switch (eventType) {
            case CREATE -> logger.info("Notificación: Nueva orden creada para el cliente -> {}", order.getClient().getName());
            case UPDATE -> logger.info("Notificación: Orden actualizada -> ID {}", order.getId());
            case DELETE -> logger.info("Notificación: Orden eliminada -> ID {}", order.getId());
        }
    }
}
