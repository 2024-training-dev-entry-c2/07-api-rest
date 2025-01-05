package com.example.restaurant_management.observers;

import com.example.restaurant_management.constants.EventType;
import com.example.restaurant_management.models.Client;
import com.example.restaurant_management.observers.interfaces.IObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ClientNotificationObserver implements IObserver<Client> {

    private static final Logger logger = LoggerFactory.getLogger(ClientNotificationObserver.class);

    @Override
    public void update(EventType eventType, Client client) {
        switch (eventType) {
            case CREATE -> logger.info("Notificación: Nuevo cliente registrado -> {}", client.getName());
            case UPDATE -> logger.info("Notificación: Cliente actualizado -> {}", client.getName());
            case DELETE -> logger.info("Notificación: Cliente eliminado -> {}", client.getName());
        }
    }
}
