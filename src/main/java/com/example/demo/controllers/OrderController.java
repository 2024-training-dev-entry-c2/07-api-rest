package com.example.demo.controllers;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.models.Order;
import com.example.demo.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
        if (orderDTO.getClient().getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        service.createOrder(Order.builder().client(orderDTO.getClient())
                .localDate(orderDTO.getLocalDate())
                .dishfoods(orderDTO.getDishfoods())
                .build());
        return ResponseEntity.ok("Todo oka");
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findMenuById(@PathVariable Long id) {
        Optional<Order> orderOptional = service.findOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            OrderDTO orderDTO = OrderDTO.builder()
                    .id(order.getId())
                    .client(order.getClient())
                    .localDate(order.getLocalDate())
                    .dishfoods(order.getDishfoods())
                    .build();
            return ResponseEntity.ok(orderDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/all")
    public ResponseEntity<?> findAllMenu() {
        List<OrderDTO> orderDTOS = service.findAllOrder()
                .stream().map(order -> OrderDTO.builder()
                        .id(order.getId())
                        .client(order.getClient())
                        .localDate(order.getLocalDate())
                        .dishfoods(order.getDishfoods())
                        .build())

                .toList();
        return ResponseEntity.ok(orderDTOS);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeMenu(@PathVariable Long id) {
        if (service.findOrderById(id).isPresent()) {
            service.removeOrder(id);
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.notFound().build();
    }

}

