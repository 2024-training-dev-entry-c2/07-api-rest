package com.restaurant.restaurant.controllers;

import com.restaurant.restaurant.dtos.OrderDTO;
import com.restaurant.restaurant.models.OrderModel;
import com.restaurant.restaurant.services.OrderService;
import com.restaurant.restaurant.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  @Autowired
  private OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO){
    OrderModel order = MapperUtil.mapToOrderModel(orderDTO);
    OrderModel createdOrder = orderService.createOrder(order);
    return ResponseEntity.status(HttpStatus.CREATED).body(MapperUtil.mapToOrderDTO(createdOrder));
  }

  @GetMapping
  public ResponseEntity<List<OrderDTO>> getOrders(){
    List<OrderModel> orders = orderService.getOrders();
    List<OrderDTO> orderDTOs = orders.stream().map(MapperUtil::mapToOrderDTO).collect(Collectors.toList());
    return ResponseEntity.ok(orderDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO){
    OrderModel order = MapperUtil.mapToOrderModel(orderDTO);
    OrderModel updatedOrder = orderService.updateOrder(id, order);
    return ResponseEntity.ok(MapperUtil.mapToOrderDTO(updatedOrder));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}
