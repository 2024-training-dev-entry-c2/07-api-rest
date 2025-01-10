package com.restaurant.restaurant.controllers;

import com.restaurant.restaurant.dtos.CreateOrderDTO;
import com.restaurant.restaurant.dtos.OrderDTO;
import com.restaurant.restaurant.services.OrderService;
import com.restaurant.restaurant.utils.ApiResponse;
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
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders(){
    List<OrderDTO> orders = orderService.findAll();
    return ResponseEntity.ok(ApiResponse.success(orders));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(@PathVariable Long id) {
    OrderDTO order = orderService.findById(id);
    return ResponseEntity.ok(ApiResponse.success(order));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@RequestBody CreateOrderDTO createOrderDTO){
    OrderDTO createdOrder = orderService.createOrder(createOrderDTO);
    return new ResponseEntity<>(ApiResponse.success("Success Created Order", createdOrder), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<OrderDTO>> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO){
    OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
    return ResponseEntity.ok(ApiResponse.success("Success Updated Order", updatedOrder));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id){
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}
