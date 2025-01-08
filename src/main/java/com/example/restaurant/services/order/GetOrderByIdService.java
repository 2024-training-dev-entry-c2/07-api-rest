package com.example.restaurant.services.order;

import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetOrderByIdService implements ICommandParametrized<Order, Long> {

    private final IOrderRepository orderRepository;

    @Autowired
    public GetOrderByIdService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order execute(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden con el id " + id + " no encontrada"));
    }
}
