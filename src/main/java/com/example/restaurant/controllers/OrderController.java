package com.example.restaurant.controllers;

import com.example.restaurant.dto.order.OrderRequestDTO;
import com.example.restaurant.dto.order.OrderResponseDTO;
import com.example.restaurant.models.Order;
import com.example.restaurant.services.client.GetClientByIdService;
import com.example.restaurant.services.dish.GetDishByIdService;
import com.example.restaurant.services.order.CreateOrderService;
import com.example.restaurant.services.order.DeleteOrderService;
import com.example.restaurant.services.order.GetAllOrdersService;
import com.example.restaurant.services.order.GetOrderByIdService;
import com.example.restaurant.services.order.UpdateOrderService;
import com.example.restaurant.utils.converter.OrderDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
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
public class OrderController {
    private final GetClientByIdService getClientByIdService;
    private final GetDishByIdService getDishByIdService;
    private final CreateOrderService createOrderService;
    private final GetOrderByIdService getOrderByIdService;
    private final GetAllOrdersService getAllOrdersService;
    private final UpdateOrderService updateOrderService;
    private final DeleteOrderService deleteOrderService;

    @Autowired
    public OrderController(GetClientByIdService getClientByIdService, GetDishByIdService getDishByIdService, CreateOrderService createOrderService, GetOrderByIdService getOrderByIdService, GetAllOrdersService getAllOrdersService, UpdateOrderService updateOrderService, DeleteOrderService deleteOrderService) {
        this.getClientByIdService = getClientByIdService;
        this.getDishByIdService = getDishByIdService;
        this.createOrderService = createOrderService;
        this.getOrderByIdService = getOrderByIdService;
        this.getAllOrdersService = getAllOrdersService;
        this.updateOrderService = updateOrderService;
        this.deleteOrderService = deleteOrderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        Order orderEntity = OrderDtoConverter.convertToEntity(
                getClientByIdService.execute(orderRequestDTO.getClientId()),
                orderRequestDTO.getDishIds().stream().map(getDishByIdService::execute).toList()
        );
        Order newOrder = createOrderService.execute(orderEntity.getClient().getId(), orderRequestDTO.getDishIds());
        OrderResponseDTO responseDTO = OrderDtoConverter.convertToDto(newOrder);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        Order order = getOrderByIdService.execute(id);
        OrderResponseDTO responseDTO = OrderDtoConverter.convertToDto(order);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<Order> orders = getAllOrdersService.execute();
        List<OrderResponseDTO> responseDTOs = orders.stream()
                .map(OrderDtoConverter::convertToDto)
                .toList();
        return ResponseEntity.ok(responseDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        deleteOrderService.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        Order orderEntity = OrderDtoConverter.convertToEntity(
                getClientByIdService.execute(orderRequestDTO.getClientId()),
                orderRequestDTO.getDishIds().stream().map(getDishByIdService::execute).toList()
        );
        Order updatedOrder = updateOrderService.execute(id, orderEntity);
        OrderResponseDTO responseDTO = OrderDtoConverter.convertToDto(updatedOrder);
        return ResponseEntity.ok(responseDTO);
    }
}
