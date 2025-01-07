package com.restaurante.restaurante.controllers;

import com.restaurante.restaurante.dto.OrderDTO;
import com.restaurante.restaurante.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final IOrderService IOrderService;

    @Autowired
    public OrderController(IOrderService IOrderService) {
        this.IOrderService = IOrderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = IOrderService.addOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        return IOrderService.getOrder(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders() {
        List<OrderDTO> orders = IOrderService.getOrders();
        return orders.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO updated = IOrderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        IOrderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}