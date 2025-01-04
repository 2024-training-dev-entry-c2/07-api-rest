package com.example.demo.observer;

import com.example.demo.models.Clientorder;
import com.example.demo.repositories.OrderRepository;

public class clienteObserver implements Observer {
    private final Clientorder client;
    private final OrderRepository orderRepository;


    public clienteObserver(Clientorder cliente, OrderRepository pedidoRepository) {
        this.client = cliente;
        this.orderRepository = pedidoRepository;
    }

    @Override
    public void update() {
        long orderQuantity = orderRepository.countByClient_Id(client.getId());
        if (orderQuantity >= 10 && !client.getIsOften()) {
            client.setIsOften(true);
            System.out.println("El cliente " + client.getName() + " ahora es frecuente.");
        }
    }
}
