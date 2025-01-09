package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.dto.CompleteOrderRequestDTO;
import com.restaurant.restaurant_management.dto.DetailRequestDTO;
import com.restaurant.restaurant_management.dto.OrderRequestDTO;
import com.restaurant.restaurant_management.dto.OrderResponseDTO;
import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.models.ClientOrder;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.models.OrderDetail;
import com.restaurant.restaurant_management.services.ClientService;
import com.restaurant.restaurant_management.services.DishService;
import com.restaurant.restaurant_management.services.OrderDetailService;
import com.restaurant.restaurant_management.services.OrderService;
import com.restaurant.restaurant_management.utils.DtoOrderConverter;
import com.restaurant.restaurant_management.utils.DtoOrderDetailConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
  private final OrderService orderService;
  private final ClientService clientService;
  private final DishService dishService;
  private final OrderDetailService orderDetailService;

  public OrderController(OrderService orderService, ClientService clientService, DishService dishService, OrderDetailService orderDetailService) {
    this.orderService = orderService;
    this.clientService = clientService;
    this.dishService = dishService;
    this.orderDetailService = orderDetailService;
  }

  @PostMapping
  public ResponseEntity<OrderResponseDTO> saveOrder(@RequestBody CompleteOrderRequestDTO completeOrderRequestDTO) {
    try{
      OrderRequestDTO orderRequestDTO = completeOrderRequestDTO.getOrderRequestDTO();
      Client client = clientService.getClient(orderRequestDTO.getClientId()).orElseThrow();
      ClientOrder order = DtoOrderConverter.convertToOrder(orderRequestDTO, client);
      ClientOrder newOrder = orderService.saveOrder(order);
      List<DetailRequestDTO> details = completeOrderRequestDTO.getDetails();
      List<OrderDetail> orderDetails = new ArrayList<>();
      for (DetailRequestDTO detail : details) {
        Dish dish = dishService.getDish(detail.getDishId()).orElseThrow();
        orderDetails.add(DtoOrderDetailConverter.convertToOrderDetail(detail, newOrder, dish));
      }
      newOrder.setOrderDetails(orderDetailService.saveOrderDetails(orderDetails));
      orderService.updateTotalOrder(newOrder.getId());
      return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(DtoOrderConverter.convertToResponseDTO(newOrder));
    }catch (RuntimeException e){
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id){
   return orderService.getOrder(id)
        .map(order -> ResponseEntity.ok(DtoOrderConverter.convertToResponseDTO(order)))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/by-date")
  public ResponseEntity<List<OrderResponseDTO>> getOrdersByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    List<ClientOrder> orders = orderService.findOrdersByDate(date);
    List<OrderResponseDTO> response = orders.stream()
        .map(DtoOrderConverter::convertToResponseDTO)
        .toList();
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO orderRequestDTO) {
    try {
      Client client = clientService.getClient(orderRequestDTO.getClientId()).orElseThrow();
      ClientOrder updated = orderService.updateOrder(id, DtoOrderConverter.convertToOrder(orderRequestDTO, client));
      return ResponseEntity.ok(DtoOrderConverter.convertToResponseDTO(updated));
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