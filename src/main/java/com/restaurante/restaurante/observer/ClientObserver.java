package com.restaurante.restaurante.observer;

import com.restaurante.restaurante.repositories.OrderRepository;
import com.restaurante.restaurante.services.IClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ClientObserver implements OrderObserver {
    private final IClientService clientService;
    private final OrderRepository orderRepository;
    private static final Logger logger = LoggerFactory.getLogger(ClientObserver.class);

    public ClientObserver(IClientService clientService, OrderRepository orderRepository) {
        this.clientService = clientService;
        this.orderRepository = orderRepository;
    }

    @Override
    public void update(Long orderId) {
        orderRepository.findById(orderId).ifPresent(order -> {
            clientService.checkAndUpdateClientStatus(order.getClient().getId());
            logger.info("ClientObserver: Estado del cliente con ID: {} actualizado", order.getClient().getId());
        });
    }
}