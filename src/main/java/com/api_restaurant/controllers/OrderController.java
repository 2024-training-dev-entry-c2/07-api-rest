package com.api_restaurant.controllers;

import com.api_restaurant.dto.order.OrderRequestDTO;
import com.api_restaurant.dto.order.OrderResponseDTO;
import com.api_restaurant.models.Order;
import com.api_restaurant.services.OrderService;
import com.api_restaurant.utils.mapper.OrderDtoConvert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;
    private final OrderDtoConvert orderDtoConvert;

    public OrderController(OrderService service, OrderDtoConvert orderDtoConvert) {
        this.service = service;
        this.orderDtoConvert = orderDtoConvert;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponseDTO> addOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            Order order = orderDtoConvert.convertToEntity(orderRequestDTO);
            service.addOrder(order);
            OrderResponseDTO response = orderDtoConvert.convertToResponseDto(order);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id) {
        return service.getOrder(id)
                .map(order -> ResponseEntity.ok(orderDtoConvert.convertToResponseDto(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {
        List<Order> orders = service.getOrders();
        List<OrderResponseDTO> response = orders.stream()
                .map(orderDtoConvert::convertToResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            Order order = orderDtoConvert.convertToEntity(orderRequestDTO);
            service.updateOrder(id, order);
            return ResponseEntity.ok("Order actualizada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
}


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        try {
            service.deleteOrder(id);
            return ResponseEntity.ok("Orden eliminada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}