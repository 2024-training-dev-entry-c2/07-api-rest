package com.restaurant.management.controllers;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Order;
import com.restaurant.management.models.dto.DishOrderRequestDTO;
import com.restaurant.management.models.dto.OrderRequestDTO;
import com.restaurant.management.models.dto.OrderResponseDTO;
import com.restaurant.management.services.ClientService;
import com.restaurant.management.services.DishService;
import com.restaurant.management.services.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderControllerTest {
  private final WebTestClient webTestClient;
  private final OrderService orderService;
  private final ClientService clientService;
  private final DishService dishService;

  public OrderControllerTest() {
    this.orderService = mock(OrderService.class);
    this.clientService = mock(ClientService.class);
    this.dishService = mock(DishService.class);
    this.webTestClient = WebTestClient.bindToController(new OrderController(orderService, dishService, clientService)).build();
  }

  @Test
  @DisplayName("Agregar pedido")
  void addOrder() {

    OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1L,getDishOrderRequestList(), LocalDate.parse("2025-01-08"));
    Client client = new Client(1L, "name", "email");
    Order order = new Order(1L, client, LocalDate.parse("2025-01-08"));
    when(clientService.getClientById(any(Long.class))).thenReturn(Optional.of(client));
    when(dishService.getDishById(any(Long.class))).thenReturn(Optional.of(mock(Dish.class)));
    when(orderService.addOrder(any(Order.class))).thenReturn(order);

    webTestClient.post()
      .uri("/api/pedidos")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(orderRequestDTO)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(OrderResponseDTO.class)
      .value(orderResponseDTO -> {
        assertEquals(orderResponseDTO.getId(), order.getId());
        assertEquals(orderResponseDTO.getClient().getId(), order.getClient().getId());
        assertEquals(orderResponseDTO.getDate(), order.getDate());
        assertEquals(orderResponseDTO.getDishes().length, order.getOrderDishes().size());
      });

    verify(orderService).addOrder(any(Order.class));
  }

  @Test
  @DisplayName("Agregar pedido con error")
  void addOrderError() {
    OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1L, getDishOrderRequestList(), LocalDate.parse("2025-01-08"));
    Client client = new Client(1L, "name", "email");
    Order order = new Order(1L, client, LocalDate.parse("2025-01-08"));
    when(clientService.getClientById(any(Long.class))).thenReturn(Optional.of(client));
    when(dishService.getDishById(any(Long.class))).thenReturn(Optional.of(mock(Dish.class)));
    when(orderService.addOrder(any(Order.class))).thenThrow(new RuntimeException());

    webTestClient.post()
      .uri("/api/pedidos")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(orderRequestDTO)
      .exchange()
      .expectStatus().is4xxClientError();

    verify(orderService).addOrder(any(Order.class));
  }

  @Test
  @DisplayName("Obtener pedido por id")
  void getOrder() {
    Client client = new Client(1L, "name", "email");
    Order order = new Order(1L, client, LocalDate.parse("2025-01-08"));
    when(orderService.getOrderById(any(Long.class))).thenReturn(Optional.of(order));


    webTestClient.get()
      .uri("/api/pedidos/{id}", 1L)
      .exchange()
      .expectStatus().isOk()
      .expectBody(OrderResponseDTO.class)
      .value(orderResponseDTO -> {
        assertEquals(orderResponseDTO.getId(), order.getId());
        assertEquals(orderResponseDTO.getDate(), order.getDate());
        assertEquals(orderResponseDTO.getClient().getId(), order.getClient().getId());
        assertEquals(orderResponseDTO.getDishes().length, order.getOrderDishes().size());
      });

    verify(orderService).getOrderById(any(Long.class));

  }

  @Test
  @DisplayName("Obtener lista de pedidos")
  void getOrders() {
    when(orderService.getOrders()).thenReturn(getOrderList());

    webTestClient.get()
      .uri("/api/pedidos")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(OrderResponseDTO.class)
      .hasSize(3)
      .value(orderResponseDTOList -> {
        assertEquals(orderResponseDTOList.get(0).getId(), getOrderList().get(0).getId());
        assertEquals(orderResponseDTOList.get(1).getId(), getOrderList().get(1).getId());
        assertEquals(orderResponseDTOList.get(2).getId(), getOrderList().get(2).getId());
      });

    verify(orderService).getOrders();
  }

  @Test
  @DisplayName("Actualizar pedido")
  void updateOrder() {
    OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1L,getDishOrderRequestList(), LocalDate.parse("2025-01-08"));
    Client client = new Client(1L, "name", "email");
    Order order = new Order(1L, client, LocalDate.parse("2025-01-08"));
    when(clientService.getClientById(any(Long.class))).thenReturn(Optional.of(client));
    when(dishService.getDishById(any(Long.class))).thenReturn(Optional.of(mock(Dish.class)));
    when(orderService.updateOrder(any(Long.class), any(Order.class))).thenReturn(order);

    webTestClient.put()
      .uri("/api/pedidos/{id}", 1L)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(orderRequestDTO)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(OrderResponseDTO.class)
      .value(orderResponseDTO -> {
        assertEquals(orderResponseDTO.getId(), order.getId());
        assertEquals(orderResponseDTO.getClient().getId(), order.getClient().getId());
        assertEquals(orderResponseDTO.getDate(), order.getDate());
        assertEquals(orderResponseDTO.getDishes().length, order.getOrderDishes().size());
      });

    verify(orderService).updateOrder(any(Long.class), any(Order.class));
  }

  @Test
  @DisplayName("Actualizar pedido con error")
  void updateOrderError() {
    OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1L,getDishOrderRequestList(), LocalDate.parse("2025-01-08"));
    Client client = new Client(1L, "name", "email");
    Order order = new Order(1L, client, LocalDate.parse("2025-01-08"));
    when(clientService.getClientById(any(Long.class))).thenReturn(Optional.of(client));
    when(dishService.getDishById(any(Long.class))).thenReturn(Optional.of(mock(Dish.class)));
    when(orderService.updateOrder(any(Long.class), any(Order.class))).thenThrow(new RuntimeException());

    webTestClient.put()
      .uri("/api/pedidos/{id}", 1L)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(orderRequestDTO)
      .exchange()
      .expectStatus().is4xxClientError();

    verify(orderService).updateOrder(any(Long.class), any(Order.class));
  }

  @Test
  @DisplayName("Eliminar pedido")
  void deleteOrder() {
    doNothing().when(orderService).deleteOrder(any(Long.class));

    webTestClient.delete()
      .uri("/api/pedidos/{id}", 1L)
      .exchange()
      .expectStatus().isNoContent();

    verify(orderService).deleteOrder(any(Long.class));
  }

  private List<Order> getOrderList(){
    return List.of(new Order(1L, new Client(1L, "name1", "email1"), LocalDate.parse("2025-01-08")),
      new Order(2L, new Client(2L, "name2", "email2"), LocalDate.parse("2025-01-08")),
      new Order(3L, new Client(3L, "name3", "email3"), LocalDate.parse("2025-01-08")));
  }

  private List<DishOrderRequestDTO> getDishOrderRequestList(){
    return List.of( new DishOrderRequestDTO(1L, 2),
      new DishOrderRequestDTO(2L, 2),
      new DishOrderRequestDTO(3L, 2)
    );
  }
}