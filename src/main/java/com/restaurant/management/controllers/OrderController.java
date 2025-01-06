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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<String> addOrder(@RequestBody OrderRequestDTO orderRequestDTO){
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
      service.addOrder(order);
      service.applyDiscounts(order);
      return ResponseEntity.ok("Pedido agregado éxitosamente con descuentos aplicados.");
    }catch (RuntimeException e){
      return ResponseEntity.badRequest().body("Error al agregar el pedido: " + e.getMessage());
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
  public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO orderRequestDTO){
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
      service.applyDiscounts(updatedOrder);
      return ResponseEntity.ok("Se ha actualizado exitosamente el pedido con descuentos aplicados.");
    } catch (RuntimeException e){
      return ResponseEntity.badRequest().body("Error al actualizar el pedido: " + e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
    service.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}
