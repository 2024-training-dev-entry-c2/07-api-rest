package com.restaurant.management.controllers;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Order;
import com.restaurant.management.models.OrderDish;
import com.restaurant.management.models.dto.DishOrderRequestDTO;
import com.restaurant.management.models.dto.OrderRequestDTO;
import com.restaurant.management.models.dto.OrderResponseDTO;
import com.restaurant.management.services.ClientService;
import com.restaurant.management.services.DishService;
import com.restaurant.management.services.OrderService;
import com.restaurant.management.utils.DtoOrderConverter;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/pedidos")
public class OrderController {
  private final OrderService service;
  private final DishService dishService;
  private final ClientService clientService;
  
  @Autowired
  public OrderController(OrderService service, DishService dishService, ClientService clientService) {
    this.service = service;
    this.dishService = dishService;
    this.clientService = clientService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OrderResponseDTO addOrder(@RequestBody OrderRequestDTO orderRequestDTO){
    try {
      Client client = clientService.getClientById(orderRequestDTO.getClientId())
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

      List<OrderDish> orderDishes = new ArrayList<>();
      for (DishOrderRequestDTO dishOrderRequestDTO : orderRequestDTO.getDishes()) {
        Dish dish = dishService.getDishById(dishOrderRequestDTO.getDishId())
          .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        orderDishes.add(new OrderDish( dish, dishOrderRequestDTO.getQuantity()));
      }

      Order order = DtoOrderConverter.toOrder(orderRequestDTO, client, orderDishes);

      return DtoOrderConverter.toOrderResponseDTO(service.addOrder(order));
    }catch (RuntimeException e){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id){
    return service.getOrderById(id)
      .map(order -> ResponseEntity.ok(DtoOrderConverter.toOrderResponseDTO(order)))
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<OrderResponseDTO>> getOrders(){
    List<OrderResponseDTO> orders = service.getOrders().stream()
      .map(DtoOrderConverter::toOrderResponseDTO)
      .toList();
    return ResponseEntity.ok(orders);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO orderRequestDTO){
    try{
      Client client = clientService.getClientById(orderRequestDTO.getClientId())
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

      List<OrderDish> orderDishes = new ArrayList<>();
      for (DishOrderRequestDTO dishOrderRequestDTO : orderRequestDTO.getDishes()) {
        Dish dish = dishService.getDishById(dishOrderRequestDTO.getDishId())
          .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        orderDishes.add(new OrderDish( dish, dishOrderRequestDTO.getQuantity()));
      }

      Order order = DtoOrderConverter.toOrder(orderRequestDTO, client, orderDishes);
      Order updatedOrder = service.updateOrder(id, order);
      return ResponseEntity.ok(DtoOrderConverter.toOrderResponseDTO(updatedOrder));
    } catch (RuntimeException e){
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
    service.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}
