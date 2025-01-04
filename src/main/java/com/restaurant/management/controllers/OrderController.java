package com.restaurant.management.controllers;

import com.restaurant.management.models.Order;
import com.restaurant.management.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class OrderController {
  private final OrderService service;
  
  @Autowired
  public OrderController(OrderService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<String> addOrder(@RequestBody Order order){
    service.addOrder(order);
    return ResponseEntity.ok("Pedido agregado éxitosamente");
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrder(@PathVariable Long id){
    return service.getOrderById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Order>> getOrders(){
    return ResponseEntity.ok(service.getOrders());
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody Order order){
    try{
      Order updatedOrder = service.updateOrder(id, order);
      return ResponseEntity.ok("Se ha actualizado exitosamente el pedido.");
    } catch (RuntimeException e){
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteOrder(@PathVariable Long id){
    service.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}
