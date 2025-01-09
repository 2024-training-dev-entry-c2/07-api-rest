package com.example.demo.controllers;

import com.example.demo.DTO.order.OrderRequestDTO;
import com.example.demo.DTO.order.OrderResponseDTO;
import com.example.demo.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(OrderController.class)
class OrderControllerTest {
    @MockBean
    private OrderService orderService;

    @Autowired
    private WebTestClient webTestClient;

    private OrderRequestDTO orderRequestDTO;
    private OrderResponseDTO orderResponseDTO;

    @BeforeEach
    void setUp() {
        orderRequestDTO = new OrderRequestDTO(1L, LocalDate.parse("2025-01-07"), List.of());
        orderResponseDTO = OrderResponseDTO.builder()
                .id(1L)
                .localDate(LocalDate.parse("2025-01-07"))
                .dishfoodIds(List.of())
                .build();
    }

    @Test
    void createOrder() {
        when(orderService.createOrder(any(OrderRequestDTO.class))).thenReturn(orderResponseDTO);
        webTestClient.post()
                .uri("/order")
                .bodyValue(orderResponseDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderResponseDTO.class)
                .value(response -> {
                    assertEquals(orderResponseDTO.getId(), response.getId());
                    assertEquals(orderResponseDTO.getClient(), response.getClient());
                    assertEquals(orderResponseDTO.getDishfoodIds(), response.getDishfoodIds());
                });
        verify(orderService).createOrder(any(OrderRequestDTO.class));

    }

    @Test
    void getOrderById() {

        when(orderService.getOrderById(anyLong())).thenReturn(orderResponseDTO);
        webTestClient.get()
                .uri("/order/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderResponseDTO.class)
                .value(response -> {
                    assertEquals(orderResponseDTO.getId(), response.getId());
                    assertEquals(orderResponseDTO.getLocalDate(), response.getLocalDate());
                    assertEquals(orderResponseDTO.getClient(), response.getClient());
                    assertEquals(orderResponseDTO.getDishfoodIds(), response.getDishfoodIds());
                });
        verify(orderService).getOrderById(anyLong());
    }

    @Test
    void getAllOrders() {
        when(orderService.getAllOrders()).thenReturn(getOrderList());
        webTestClient.get()
                .uri("/order")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(OrderResponseDTO.class)
                .hasSize(3)
                .value(response->{
                    assertEquals(LocalDate.parse("2025-01-07"), response.get(0).getLocalDate());
                    assertEquals(LocalDate.parse("2025-01-07"), response.get(1).getLocalDate());
                    assertEquals(LocalDate.parse("2025-01-07"), response.get(2).getLocalDate());
                });
        verify(orderService).getAllOrders();
    }

    @Test
    void updateMenu() {
        when(orderService.updateOrder(anyLong(),any(OrderRequestDTO.class))).thenReturn(orderResponseDTO);
        webTestClient.put()
                .uri("/order/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(orderResponseDTO)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderResponseDTO.class)
                .value(response->{
                    assertEquals(orderResponseDTO.getId(), response.getId());
                    assertEquals(orderResponseDTO.getLocalDate(), response.getLocalDate());
                    assertEquals(orderResponseDTO.getClient(), response.getClient());
                    assertEquals(orderResponseDTO.getDishfoodIds(), response.getDishfoodIds());
                });
        verify(orderService).updateOrder(anyLong(),any(OrderRequestDTO.class));
    }

    @Test
    void deleteOrder() {
        doNothing().when(orderService).removeOrder(anyLong());
        webTestClient.delete()
                .uri("/order/delete/{id}",1L)
                .exchange()
                .expectStatus().isNoContent();
    }

    private List<OrderResponseDTO> getOrderList() {
        return List.of(
                OrderResponseDTO.builder()
                        .id(1L)
                        .localDate(LocalDate.parse("2025-01-07"))
                        .dishfoodIds(List.of())
                        .build(),

                OrderResponseDTO.builder()
                        .id(2L)
                        .localDate(LocalDate.parse("2025-01-07"))
                        .dishfoodIds(List.of())
                        .build(),

                OrderResponseDTO.builder()
                        .id(3L)
                        .localDate(LocalDate.parse("2025-01-07"))
                        .dishfoodIds(List.of())
                        .build()


        );
    }
}