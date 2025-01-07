package com.example.restaurant.controllers;

import com.example.restaurant.models.dto.order.OrderRequestDTO;
import com.example.restaurant.models.dto.order.OrderResponseDTO;
import com.example.restaurant.services.order.OrderCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderCommandHandler orderCommandHandler;

  @PostMapping
  public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderDTO) {
    OrderResponseDTO createdOrder = orderCommandHandler.createOrder(orderDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
    OrderResponseDTO order = orderCommandHandler.getOrderById(id);
    return ResponseEntity.ok(order);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> updateOrder(
          @PathVariable Long id,
          @RequestBody OrderRequestDTO orderDTO) {
    OrderResponseDTO updatedOrder = orderCommandHandler.updateOrder(id, orderDTO);
    return ResponseEntity.ok(updatedOrder);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderCommandHandler.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<OrderResponseDTO>> listOrders() {
    List<OrderResponseDTO> orders = orderCommandHandler.listOrders();
    return ResponseEntity.ok(orders);
  }
}
