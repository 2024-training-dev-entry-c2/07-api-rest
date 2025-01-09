package com.api_restaurant.controllers;

import com.api_restaurant.dto.order.OrderRequestDTO;
import com.api_restaurant.dto.order.OrderResponseDTO;
import com.api_restaurant.models.Order;
import com.api_restaurant.services.OrderService;
import com.api_restaurant.utils.mapper.OrderDtoConvert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderControllerTest {
    private final WebTestClient webTestClient;
    private final OrderService orderService;
    private final OrderDtoConvert orderDtoConvert;

    public OrderControllerTest() {
        orderService = mock(OrderService.class);
        orderDtoConvert = mock(OrderDtoConvert.class);
        webTestClient = WebTestClient.bindToController(new OrderController(orderService, orderDtoConvert)).build();
    }

    @Test
    @DisplayName("Agregar Orden")
    void addOrder() {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        Order order = new Order();
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

        when(orderDtoConvert.convertToEntity(any(OrderRequestDTO.class))).thenReturn(order);
        when(orderService.addOrder(any(Order.class))).thenReturn(order);
        when(orderDtoConvert.convertToResponseDto(any(Order.class))).thenReturn(orderResponseDTO);

        webTestClient.post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(orderRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(OrderResponseDTO.class)
                .value(response -> {
                    assertNotNull(response);
                });

        Mockito.verify(orderService).addOrder(any(Order.class));
    }

    @Test
    @DisplayName("Obtener Orden por ID")
    void getOrder() {
        Order order = new Order();
        order.setId(1L);
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(1L);

        when(orderService.getOrder(1L)).thenReturn(Optional.of(order));
        when(orderDtoConvert.convertToResponseDto(any(Order.class))).thenReturn(orderResponseDTO);

        webTestClient.get()
                .uri("/orders/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(OrderResponseDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response.getId(), orderResponseDTO.getId());
                });

        Mockito.verify(orderService).getOrder(1L);
    }

    @Test
    @DisplayName("Obtener Orden por ID - No Encontrado")
    void getOrderNotFound() {
        when(orderService.getOrder(1L)).thenReturn(Optional.empty());

        webTestClient.get()
                .uri("/orders/1")
                .exchange()
                .expectStatus().isNotFound();

        Mockito.verify(orderService).getOrder(1L);
    }

    @Test
    @DisplayName("Obtener Todas las Ordenes")
    void getOrders() {
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);

        OrderResponseDTO orderResponseDTO1 = new OrderResponseDTO();
        orderResponseDTO1.setId(1L);
        OrderResponseDTO orderResponseDTO2 = new OrderResponseDTO();
        orderResponseDTO2.setId(2L);

        when(orderService.getOrders()).thenReturn(List.of(order1, order2));
        when(orderDtoConvert.convertToResponseDto(order1)).thenReturn(orderResponseDTO1);
        when(orderDtoConvert.convertToResponseDto(order2)).thenReturn(orderResponseDTO2);

        webTestClient.get()
                .uri("/orders")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(OrderResponseDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(2, response.size());
                    assertEquals(response.get(0).getId(), orderResponseDTO1.getId());
                    assertEquals(response.get(1).getId(), orderResponseDTO2.getId());
                });

        Mockito.verify(orderService).getOrders();
    }

    @Test
    @DisplayName("Actualizar Orden")
    void updateOrder() {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        Order order = new Order();
        order.setId(1L);

        when(orderDtoConvert.convertToEntity(any(OrderRequestDTO.class))).thenReturn(order);
        when(orderService.updateOrder(any(Long.class), any(Order.class))).thenReturn(order);

        webTestClient.put()
                .uri("/orders/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(orderRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response, "Order actualizada exitosamente");
                });

        Mockito.verify(orderService).updateOrder(any(Long.class), any(Order.class));
    }

    @Test
    @DisplayName("Actualizar Orden - No Encontrado")
    void updateOrderNotFound() {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        Order order = new Order();

        when(orderDtoConvert.convertToEntity(any(OrderRequestDTO.class))).thenReturn(order);
        when(orderService.updateOrder(any(Long.class), any(Order.class)))
                .thenThrow(new RuntimeException("Order with id 1 could not be updated"));

        webTestClient.put()
                .uri("/orders/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(orderRequestDTO)
                .exchange()
                .expectStatus().isNotFound();

        Mockito.verify(orderService).updateOrder(any(Long.class), any(Order.class));
    }

    @Test
    @DisplayName("Eliminar Orden")
    void deleteOrder() {
        Mockito.doNothing().when(orderService).deleteOrder(1L);

        webTestClient.delete()
                .uri("/orders/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response, "Orden eliminada exitosamente");
                });

        Mockito.verify(orderService).deleteOrder(1L);
    }

    @Test
    @DisplayName("Eliminar Orden - No Encontrada")
    void deleteOrderNotFound() {
        Mockito.doThrow(new RuntimeException("Order with id 1 not found")).when(orderService).deleteOrder(1L);

        webTestClient.delete()
                .uri("/orders/1")
                .exchange()
                .expectStatus().isNotFound();
        Mockito.verify(orderService).deleteOrder(1L);
    }
}