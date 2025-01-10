package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.constants.OrderStatus;
import com.restaurant.restaurant_management.dto.CompleteOrderRequestDTO;
import com.restaurant.restaurant_management.dto.DetailRequestDTO;
import com.restaurant.restaurant_management.dto.OrderRequestDTO;
import com.restaurant.restaurant_management.dto.OrderResponseDTO;
import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.models.ClientOrder;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.models.Menu;
import com.restaurant.restaurant_management.models.OrderDetail;
import com.restaurant.restaurant_management.services.ClientService;
import com.restaurant.restaurant_management.services.DishService;
import com.restaurant.restaurant_management.services.OrderDetailService;
import com.restaurant.restaurant_management.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderControllerTest {

  private final WebTestClient webTestClient;
  private final OrderService orderService;
  private final ClientService clientService;
  private final DishService dishService;
  private final OrderDetailService orderDetailService;

  private Menu menu;
  private Dish dish;
  private Client client;
  private ClientOrder order;
  private OrderRequestDTO orderRequestDTO;
  private OrderResponseDTO orderResponseDTO;
  private List<OrderDetail> orderDetailList;
  private List<DetailRequestDTO> detailRequestDTOList;
  private CompleteOrderRequestDTO completeOrderRequestDTO;
  private List<OrderResponseDTO> orderResponseDTOList;
  private List<ClientOrder> orderList;

  public OrderControllerTest() {
    orderService = mock(OrderService.class);
    clientService = mock(ClientService.class);
    dishService = mock(DishService.class);
    orderDetailService = mock(OrderDetailService.class);
    webTestClient = WebTestClient.bindToController(new OrderController(orderService, clientService, dishService, orderDetailService)).build();
  }

  @BeforeEach
  void setUp() {
    menu = new Menu(1, "Menu 1", "Descripcion del menu 1", true);
    dish = new Dish(1, "Pollo", "Ocho presas", 45000, true, true, menu);
    client = new Client(1L,"Juan","Perez","juanperez@gmail.com","123456789","Av. de la Independencia, 1",true);
    order = new ClientOrder(1L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING,90000.0,client);
    orderRequestDTO = new OrderRequestDTO(1L,OrderStatus.PROCESSING);
    orderDetailList = List.of(
      new OrderDetail(1L,2,90000.0,order,dish),
      new OrderDetail(2L,1,45000.0,order,dish)
    );
    orderResponseDTO = new OrderResponseDTO(1L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING, 90000.0, client,orderDetailList);
    detailRequestDTOList = List.of(
      new DetailRequestDTO(2,1),
      new DetailRequestDTO(1,1)
    );
    completeOrderRequestDTO = new CompleteOrderRequestDTO(orderRequestDTO,detailRequestDTOList);
    orderList = List.of(
      new ClientOrder(1L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING,125000.0,client),
      new ClientOrder(2L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING,34000.0,client),
      new ClientOrder(3L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING,25000.0,client)
    );
    orderResponseDTOList = List.of(
      new OrderResponseDTO(1L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING, 125000.0, client,orderDetailList),
      new OrderResponseDTO(2L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING, 34000.0, client,orderDetailList),
      new OrderResponseDTO(3L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING, 25000.0, client,orderDetailList)
    );

  }

  @Test
  @DisplayName("Save Order")
  void saveOrder() {
    when(clientService.getClient(any(Long.class))).thenReturn(Optional.of(client));
    when(orderService.saveOrder(any(ClientOrder.class))).thenReturn(order);
    when(dishService.getDish(any(Integer.class))).thenReturn(Optional.of(dish));
    when(orderDetailService.saveOrderDetails(any(List.class))).thenReturn(orderDetailList);
    doNothing().when(orderService).updateTotalOrder(any(Long.class));

    webTestClient.post()
      .uri("/api/order")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(completeOrderRequestDTO)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(OrderResponseDTO.class)
      .value(order1->{
        assertEquals(orderResponseDTO.getId(), order1.getId());
        assertEquals(orderResponseDTO.getOrderDateTime(), order1.getOrderDateTime());
        assertEquals(orderResponseDTO.getStatus(), order1.getStatus());
        assertEquals(orderResponseDTO.getTotal(), order1.getTotal());
      });

    Mockito.verify(orderService).saveOrder(any(ClientOrder.class));
    Mockito.verify(orderDetailService).saveOrderDetails(any(List.class));
    Mockito.verify(orderService).updateTotalOrder(any(Long.class));
  }

  @Test
  @DisplayName("Save Order - No Client")
  void saveOrder_NoClient() {
    when(clientService.getClient(any(Long.class))).thenReturn(Optional.empty());
    webTestClient.post()
      .uri("/api/order")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(completeOrderRequestDTO)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody().isEmpty();

    Mockito.verify(orderService, Mockito.never()).saveOrder(any(ClientOrder.class));
    Mockito.verify(clientService).getClient(any(Long.class));
  }

  @Test
  @DisplayName("Get Order")
  void getOrder() {
    when(orderService.getOrder(any(Long.class))).thenReturn(Optional.of(order));

    webTestClient.get()
      .uri("/api/order/{id}", 1)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(OrderResponseDTO.class)
      .value(order1->{
        assertEquals(orderResponseDTO.getId(), order1.getId());
        assertEquals(orderResponseDTO.getOrderDateTime(), order1.getOrderDateTime());
        assertEquals(orderResponseDTO.getStatus(), order1.getStatus());
        assertEquals(orderResponseDTO.getTotal(), order1.getTotal());
      });

    Mockito.verify(orderService).getOrder(any(Long.class));
  }

  @Test
  @DisplayName("Get Orders By Date")
  void getOrdersByDate() {
    when(orderService.findOrdersByDate(any(LocalDate.class))).thenReturn(orderList);

    webTestClient.get()
      .uri(uriBuilder -> uriBuilder
        .path("/api/order/by-date")
        .queryParam("date", LocalDate.of(2025, 1, 7))
        .build())
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(OrderResponseDTO.class)
      .hasSize(3)
      .value(orders->{
        assertEquals(orderResponseDTOList.size(), orders.size());
        assertEquals(1, orders.get(0).getId());
        assertEquals(2, orders.get(1).getId());
        assertEquals(3, orders.get(2).getId());
      });
    Mockito.verify(orderService).findOrdersByDate(any(LocalDate.class));
  }

  @Test
  @DisplayName("Update Order")
  void updateOrder() {
    when(clientService.getClient(any(Long.class))).thenReturn(Optional.of(client));
    when(orderService.updateOrder(any(Long.class), any(ClientOrder.class))).thenReturn(order);

    webTestClient.put()
      .uri("/api/order/{id}", 1)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(orderRequestDTO)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(OrderResponseDTO.class)
      .value(order1->{
        assertEquals(orderResponseDTO.getId(), order1.getId());
        assertEquals(orderResponseDTO.getOrderDateTime(), order1.getOrderDateTime());
        assertEquals(orderResponseDTO.getStatus(), order1.getStatus());
        assertEquals(orderResponseDTO.getTotal(), order1.getTotal());
      });

    Mockito.verify(orderService).updateOrder(any(Long.class), any(ClientOrder.class));
  }

  @Test
  @DisplayName("Update Order - No Client")
  void updateOrder_NoClient() {
    when(clientService.getClient(any(Long.class))).thenReturn(Optional.empty());
    webTestClient.put()
      .uri("/api/order/{id}", 1)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(orderRequestDTO)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody().isEmpty();
    Mockito.verify(orderService, Mockito.never()).updateOrder(any(Long.class), any(ClientOrder.class));
    Mockito.verify(clientService).getClient(any(Long.class));
  }

  @Test
  @DisplayName("Delete Order")
  void deleteOrder() {
    doNothing().when(orderService).deleteOrder(any(Long.class));

    webTestClient.delete()
      .uri("/api/order/{id}", 1)
      .exchange()
      .expectStatus().isNoContent();

    Mockito.verify(orderService).deleteOrder(any(Long.class));
  }
}