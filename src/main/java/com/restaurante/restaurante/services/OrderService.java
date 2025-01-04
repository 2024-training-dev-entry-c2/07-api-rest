package com.restaurante.restaurante.services;


import com.restaurante.restaurante.models.Orders;
import com.restaurante.restaurante.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Orders order){
        orderRepository.save(order);
    }

    public Optional<Orders> getOrder(Long id){
        return orderRepository.findById(id);
    }

    public List<Orders> getOrders(){
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

    public Orders updateOrder(Long id, Orders orderUpdated){
        return orderRepository.findById(id).map(x -> {
            x.setDateOrder(orderUpdated.getDateOrder());
            x.setTotalPrice(orderUpdated.getTotalPrice());
            x.setClient(orderUpdated.getClient());
            return orderRepository.save(x);
        }).orElseThrow(() -> new RuntimeException("El order con el id " + id +"no pude ser actualizado"));
    }



}
