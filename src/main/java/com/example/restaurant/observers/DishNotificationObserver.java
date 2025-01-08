package com.example.restaurant.observers;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import com.example.restaurant.observers.interfaces.IObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DishNotificationObserver implements IObserver<Dish> {

    private static final Logger logger = LoggerFactory.getLogger(DishNotificationObserver.class);

    @Override
    public void update(EventType eventType, Dish dish) {
        switch (eventType) {
            case CREATE -> logger.info("Notificación: Nuevo plato añadido -> {}", dish.getName());
            case UPDATE -> logger.info("Notificación: Plato actualizado -> {}", dish.getName());
            case DELETE -> logger.info("Notificación: Plato eliminado -> {}", dish.getName());
        }
    }
}
