package com.api_restaurant.controllers;

import com.api_restaurant.dto.order.OrderRequestDTO;
import com.api_restaurant.dto.order.OrderResponseDTO;
import com.api_restaurant.models.Order;
import com.api_restaurant.services.OrderService;
import com.api_restaurant.utils.OrderDtoConvert;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<OrderResponseDTO> addOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        Order order = orderDtoConvert.convertToEntity(orderRequestDTO);
        service.addOrder(order);
        OrderResponseDTO response = orderDtoConvert.convertToResponseDto(order);
        return ResponseEntity.ok(response);
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
        Order order = orderDtoConvert.convertToEntity(orderRequestDTO);
        service.updateOrder(id, order);
        return ResponseEntity.ok("Order actualizada exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
        return ResponseEntity.ok("Orden eliminada exitosamente");
    }
}