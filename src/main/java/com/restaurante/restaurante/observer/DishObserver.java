package com.restaurante.restaurante.observer;

import com.restaurante.restaurante.models.Orders;
import com.restaurante.restaurante.repositories.DishRepository;
import com.restaurante.restaurante.repositories.OrderRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DishObserver implements OrderObserver {

    private static final Logger logger = LoggerFactory.getLogger(DishObserver.class);
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    public DishObserver(OrderRepository orderRepository, DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public void update(Long orderId) {

        Orders order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            logger.info("Nueva orden agregada con ID: " + orderId + " y para el cliente " + order.getClient().getName());
            // Aquí puedes agregar la lógica adicional que necesites
        } else {
            logger.warn("No se encontró la orden con ID: " + orderId);
        }

    }
}