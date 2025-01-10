package com.example.demo.services;


import com.example.demo.models.Order;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientValidationService {
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;


    public ClientValidationService(ClientRepository clientRepository, OrderRepository orderRepository, NotificationService notificationService) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    public void checkClient(Order order) {
        long orderCount = orderRepository.countByClient_Id(order.getClient().getId());
        if (orderCount >= 10 && !order.getClient().getIsOften()) {
            order.getClient().setIsOften(true);
            clientRepository.save(order.getClient());
            notificationService.notifyObservers(order);

        }
    }
}

