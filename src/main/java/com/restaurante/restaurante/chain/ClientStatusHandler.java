package com.restaurante.restaurante.chain;

import com.restaurante.restaurante.models.Client;
import com.restaurante.restaurante.models.Orders;
import com.restaurante.restaurante.repositories.ClientRepository;
import com.restaurante.restaurante.repositories.OrderRepository;
import com.restaurante.restaurante.utils.UserType;
import org.springframework.stereotype.Component;

@Component
public class ClientStatusHandler extends Handler {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private static final Integer FREQUENT_THRESHOLD = 10;

    public ClientStatusHandler(OrderRepository orderRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void process(Orders order) {
        Client client = order.getClient();

        // Incrementar el contador de órdenes del cliente
        if (client.getTotalOrders() == null) {
            client.setTotalOrders(1);
        } else {
            client.setTotalOrders(client.getTotalOrders() + 1);
        }

        // Verificar si el cliente alcanza el estado de FREQUENT
        if (client.getTotalOrders() >= FREQUENT_THRESHOLD && !UserType.FREQUENT.toString().equals(client.getUserType())) {
            client.setUserType(UserType.FREQUENT.toString());
        }

        // Guardar los cambios en el cliente
        clientRepository.save(client);

        if (nextHandler != null) {
            nextHandler.process(order);
        }
    }
}