package com.restaurante.restaurante.controllers;


import com.restaurante.restaurante.models.Orders;
import com.restaurante.restaurante.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")

public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> addOrder(@RequestBody Orders order){
        orderService.addOrder(order);
        return ResponseEntity.ok("Order agregado exitosamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrder(id).orElseThrow());
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getOrders(){
        return ResponseEntity.ok(orderService.getOrders());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody Orders order){
        try{
            Orders orderUpdated = orderService.updateOrder(id, order);
            return ResponseEntity.ok("Se ha actualizado exitosamente el order");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }



}
