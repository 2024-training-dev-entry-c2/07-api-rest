package com.example.restaurant.observers;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Menu;
import com.example.restaurant.observers.interfaces.IObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MenuNotificationObserver implements IObserver<Menu> {

    private static final Logger logger = LoggerFactory.getLogger(MenuNotificationObserver.class);

    @Override
    public void update(EventType eventType, Menu menu) {
        switch (eventType) {
            case CREATE -> logger.info("Notificación: Nuevo menú creado -> {}", menu.getName());
            case UPDATE -> logger.info("Notificación: Menú actualizado -> {}", menu.getName());
            case DELETE -> logger.info("Notificación: Menú eliminado -> {}", menu.getName());
        }
    }
}
