package com.restaurant.restaurant.controllers;

import com.restaurant.restaurant.dtos.CreateOrderDTO;
import com.restaurant.restaurant.dtos.OrderDTO;
import com.restaurant.restaurant.services.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OrderControllerTest {

  private final WebTestClient webTestClient;
  private final OrderService orderService;

  public OrderControllerTest() {
    orderService = mock(OrderService.class);
    webTestClient = WebTestClient.bindToController(new OrderController(orderService)).build();
  }

  @Test
  @DisplayName("Get all orders")
  void getAllOrders() {
    OrderDTO orderDTO1 = new OrderDTO(1L, 1L, List.of(1L, 2L), LocalDateTime.now(), 29.99);
    OrderDTO orderDTO2 = new OrderDTO(2L, 2L, List.of(3L, 4L), LocalDateTime.now(), 35.49);

    when(orderService.findAll()).thenReturn(List.of(orderDTO1, orderDTO2));

    webTestClient
            .get()
            .uri("/api/orders")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data[0].id").isEqualTo(orderDTO1.getId().intValue())
            .jsonPath("$.data[1].id").isEqualTo(orderDTO2.getId().intValue());
  }

  @Test
  @DisplayName("Get order by ID")
  void getOrderById() {
    OrderDTO orderDTO = new OrderDTO(1L, 1L, List.of(1L, 2L), LocalDateTime.now(), 29.99);
    when(orderService.findById(1L)).thenReturn(orderDTO);

    webTestClient
            .get()
            .uri("/api/orders/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.id").isEqualTo(orderDTO.getId().intValue())
            .jsonPath("$.data.clientId").isEqualTo(orderDTO.getClientId().intValue());
  }

  @Test
  @DisplayName("Create order")
  void createOrder() {
    CreateOrderDTO createOrderDTO = new CreateOrderDTO();
    createOrderDTO.setClientId(1L);
    createOrderDTO.setDishIds(List.of(1L, 2L));

    OrderDTO orderDTO = new OrderDTO(1L, 1L, List.of(1L, 2L), LocalDateTime.now(), 29.99);
    when(orderService.createOrder(any(CreateOrderDTO.class))).thenReturn(orderDTO);

    webTestClient
            .post()
            .uri("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(createOrderDTO)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.clientId").isEqualTo(orderDTO.getClientId().intValue())
            .jsonPath("$.data.totalCost").isEqualTo(orderDTO.getTotalCost());
  }

  @Test
  @DisplayName("Update order")
  void updateOrder() {
    OrderDTO orderDTO = new OrderDTO(1L, 1L, List.of(1L, 2L), LocalDateTime.now(), 29.99);
    OrderDTO updatedOrderDTO = new OrderDTO(1L, 1L, List.of(1L, 2L), LocalDateTime.now(), 39.99);

    when(orderService.updateOrder(eq(1L), any(OrderDTO.class))).thenReturn(updatedOrderDTO);

    webTestClient
            .put()
            .uri("/api/orders/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(updatedOrderDTO)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.id").isEqualTo(updatedOrderDTO.getId().intValue())
            .jsonPath("$.data.totalCost").isEqualTo(updatedOrderDTO.getTotalCost());
  }

  @Test
  @DisplayName("Delete order")
  void deleteOrder() {
    doNothing().when(orderService).deleteOrder(1L);

    webTestClient
            .delete()
            .uri("/api/orders/1")
            .exchange()
            .expectStatus().isNoContent();
  }
}
