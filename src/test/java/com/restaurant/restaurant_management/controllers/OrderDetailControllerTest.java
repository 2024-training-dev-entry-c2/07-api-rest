package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.constants.OrderStatus;
import com.restaurant.restaurant_management.dto.OrderDetailRequestDTO;
import com.restaurant.restaurant_management.dto.OrderDetailResponseDTO;
import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.models.ClientOrder;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.models.Menu;
import com.restaurant.restaurant_management.models.OrderDetail;
import com.restaurant.restaurant_management.services.DishService;
import com.restaurant.restaurant_management.services.OrderDetailService;
import com.restaurant.restaurant_management.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderDetailControllerTest {

  private final WebTestClient webTestClient;
  private final OrderDetailService orderDetailService;
  private final DishService dishService;
  private final OrderService orderService;

  private Menu menu;
  private Dish dish;
  private Client client;
  private ClientOrder order;
  private OrderDetail orderDetail;
  private OrderDetailRequestDTO orderDetailRequestDTO;
  private OrderDetailResponseDTO orderDetailResponseDTO;
  private List<OrderDetailResponseDTO> orderDetailResponseDTOList;
  private List<OrderDetail> orderDetailList;

  public OrderDetailControllerTest() {
    orderDetailService = mock(OrderDetailService.class);
    dishService = mock(DishService.class);
    orderService = mock(OrderService.class);
    webTestClient = WebTestClient.bindToController(new OrderDetailController(orderDetailService, dishService, orderService)).build();
  }

  @BeforeEach
  void setUp() {
    menu = new Menu(1, "Menu 1", "Descripcion del menu 1", true);
    dish = new Dish(1, "Pollo", "Ocho presas", 45000, true, true, menu);
    client = new Client(1L,"Juan","Perez","juanperez@gmail.com","123456789","Av. de la Independencia, 1",true);
    order = new ClientOrder(1L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING,90000.0,client);
    orderDetail = new OrderDetail(1L,2,90000.0,order,dish);
    orderDetailResponseDTO = new OrderDetailResponseDTO(1L,2,90000.0,1L,dish);
    orderDetailRequestDTO = new OrderDetailRequestDTO(2,1L,1);

    orderDetailResponseDTOList = List.of(
      new OrderDetailResponseDTO(1L,2,90000.0,1L,dish),
      new OrderDetailResponseDTO(2L,1,45000.0,1L,dish),
      new OrderDetailResponseDTO(3L,3,135000.0,1L,dish)
    );
    orderDetailList = List.of(
      new OrderDetail(1L,2,90000.0,order,dish),
      new OrderDetail(2L,1,45000.0,order,dish),
      new OrderDetail(3L,3,135000.0,order,dish)
    );
  }

  @Test
  @DisplayName("Get order detail")
  void getOrderDetail() {
    when(orderDetailService.getOrderDetail(any(Long.class))).thenReturn(Optional.of(orderDetail));

    webTestClient.get()
      .uri("/api/order-detail/{id}", 1)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(OrderDetailResponseDTO.class)
      .value(orderDetail1->{
        assertEquals(orderDetailResponseDTO.getId(), orderDetail1.getId());
        assertEquals(orderDetailResponseDTO.getQuantity(), orderDetail1.getQuantity());
        assertEquals(orderDetailResponseDTO.getSubtotal(), orderDetail1.getSubtotal());
        assertEquals(orderDetailResponseDTO.getOrderId(), orderDetail1.getOrderId());
      });

    Mockito.verify(orderDetailService).getOrderDetail(any(Long.class));
  }

  @Test
  @DisplayName("Get order details by order id")
  void getOrderDetailsByOrderId() {
    when(orderDetailService.listOrderDetailsByOrderId(any(Long.class))).thenReturn(orderDetailList);

    webTestClient.get()
      .uri("/api/order-detail/order/{orderId}", 1)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(OrderDetailResponseDTO.class)
      .hasSize(3)
      .value(orderDetails->{
        assertEquals(orderDetailResponseDTOList.size(), orderDetails.size());
        assertEquals(2, orderDetails.get(0).getQuantity());
        assertEquals(1, orderDetails.get(1).getQuantity());
        assertEquals(3, orderDetails.get(2).getQuantity());
      });

    Mockito.verify(orderDetailService).listOrderDetailsByOrderId(any(Long.class));
  }

  @Test
  @DisplayName("Save order detail")
  void saveOrderDetail() {
    when(dishService.getDish(any(Integer.class))).thenReturn(Optional.of(dish));
    when(orderService.getOrder(any(Long.class))).thenReturn(Optional.of(order));
    when(orderDetailService.saveOrderDetail(any(OrderDetail.class))).thenReturn(orderDetail);
    doNothing().when(orderService).updateTotalOrder(any(Long.class));

    webTestClient.post()
      .uri("/api/order-detail")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(orderDetailRequestDTO)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(OrderDetailResponseDTO.class)
      .value(orderDetail1->{
        assertEquals(orderDetailResponseDTO.getId(), orderDetail1.getId());
        assertEquals(orderDetailResponseDTO.getQuantity(), orderDetail1.getQuantity());
        assertEquals(orderDetailResponseDTO.getSubtotal(), orderDetail1.getSubtotal());
        assertEquals(orderDetailResponseDTO.getOrderId(), orderDetail1.getOrderId());
      });

    Mockito.verify(orderDetailService).saveOrderDetail(any(OrderDetail.class));
  }

  @Test
  @DisplayName("Save order detail - No dish")
  void saveOrderDetail_NoDish() {
    when(dishService.getDish(any(Integer.class))).thenReturn(Optional.empty());
    webTestClient.post()
      .uri("/api/order-detail")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(orderDetailRequestDTO)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody().isEmpty();

    Mockito.verify(orderDetailService, Mockito.never()).saveOrderDetail(any(OrderDetail.class));
    Mockito.verify(dishService).getDish(any(Integer.class));
  }

  @Test
  @DisplayName("Update order detail")
  void updateOrderDetail() {
    when(dishService.getDish(any(Integer.class))).thenReturn(Optional.of(dish));
    when(orderService.getOrder(any(Long.class))).thenReturn(Optional.of(order));
    when(orderDetailService.updateOrderDetail(any(Long.class), any(OrderDetail.class))).thenReturn(orderDetail);
    doNothing().when(orderService).updateTotalOrder(any(Long.class));

    webTestClient.put()
      .uri("/api/order-detail/{id}", 1)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(orderDetailRequestDTO)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(OrderDetailResponseDTO.class)
      .value(orderDetail1->{
        assertEquals(orderDetailResponseDTO.getId(), orderDetail1.getId());
        assertEquals(orderDetailResponseDTO.getQuantity(), orderDetail1.getQuantity());
        assertEquals(orderDetailResponseDTO.getSubtotal(), orderDetail1.getSubtotal());
        assertEquals(orderDetailResponseDTO.getOrderId(), orderDetail1.getOrderId());
      });

    Mockito.verify(orderDetailService).updateOrderDetail(any(Long.class), any(OrderDetail.class));
  }

  @Test
  @DisplayName("Update order detail - No dish")
  void updateOrderDetail_NoDish() {
    when(dishService.getDish(any(Integer.class))).thenReturn(Optional.empty());
    webTestClient.put()
      .uri("/api/order-detail/{id}", 1)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(orderDetailRequestDTO)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody().isEmpty();
    Mockito.verify(orderDetailService, Mockito.never()).updateOrderDetail(any(Long.class), any(OrderDetail.class));
    Mockito.verify(dishService).getDish(any(Integer.class));
  }

  @Test
  @DisplayName("Delete order detail")
  void deleteOrderDetail() {
    doNothing().when(orderDetailService).deleteOrderDetail(any(Long.class));
    doNothing().when(orderService).updateTotalOrder(any(Long.class));

    webTestClient.delete()
      .uri(uriBuilder -> uriBuilder
        .path("/api/order-detail/{id}")
        .queryParam("orderId", 1L)
        .build(1L))
      .exchange()
      .expectStatus().isNoContent();

    Mockito.verify(orderDetailService).deleteOrderDetail(any(Long.class));
    Mockito.verify(orderService).updateTotalOrder(any(Long.class));
  }
}