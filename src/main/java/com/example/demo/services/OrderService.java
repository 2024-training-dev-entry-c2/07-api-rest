package com.example.demo.services;

import com.example.demo.models.Clientorder;
import com.example.demo.models.Dishfood;
import com.example.demo.models.Order;
import com.example.demo.observer.ClientObserver;
import com.example.demo.observer.DishFoodObserver;
import com.example.demo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }


    public Order createOrder(Order order) {
        Order newOrder = repository.save(order);
        Clientorder client = order.getClient();
        ClientObserver clientObserver = new ClientObserver(client, repository);
        clientObserver.update();
        for (Dishfood dishfood : order.getDishfoods()) {
            DishFoodObserver dishFoodObserver = new DishFoodObserver(dishfood, repository);
            dishFoodObserver.update();
        }
        return newOrder;
    }

    public Optional<Order> findOrderById(Long id) {
        return repository.findById(id);
    }

    public List<Order> findAllOrder() {
        return repository.findAll();
    }

    public void removeOrder(Long id) {
        repository.deleteById(id);
    }
}
