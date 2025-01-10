package com.example.restaurant.controllers;

import com.example.restaurant.models.dto.customer.CustomerResponseDTO;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import com.example.restaurant.models.dto.order.OrderRequestDTO;
import com.example.restaurant.models.dto.order.OrderResponseDTO;
import com.example.restaurant.services.order.OrderCommandHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class OrderControllerTest {

  private final OrderCommandHandler orderService;
  private final WebTestClient webTestClient;

  OrderControllerTest() {
    orderService = mock(OrderCommandHandler.class);
    this.webTestClient = WebTestClient.bindToController(new OrderController(orderService)).build();
  }


  @Test
  @DisplayName("Crear una orden")
  void createOrder() {

    CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
    customerResponseDTO.setCustomerId(1L);
    customerResponseDTO.setName("John");
    customerResponseDTO.setLastName("Doe");
    customerResponseDTO.setEmail("doe@example.com");
    customerResponseDTO.setPhone("+1 223-3343-332");

    DishResponseDTO dishResponseDTO1 = new DishResponseDTO();
    dishResponseDTO1.setDishId(1L);
    dishResponseDTO1.setName("Ceviche de camarones");
    dishResponseDTO1.setPrice(12000.0f);

    OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
    orderRequestDTO.setCustomerId(1L);
    orderRequestDTO.setDishIds(List.of(1L));

    OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
    orderResponseDTO.setOrderId(1L);
    orderResponseDTO.setCustomer(customerResponseDTO);
    orderResponseDTO.setDishes(List.of(dishResponseDTO1));

    Mockito.when(orderService.createOrder(orderRequestDTO)).thenReturn(orderResponseDTO);

    webTestClient
            .post()
            .uri("/orders")
            .bodyValue(orderRequestDTO)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(OrderResponseDTO.class)
            .value(d -> {
              assertEquals(orderResponseDTO.getOrderId(), d.getOrderId());
              assertEquals(orderResponseDTO.getCustomer(), d.getCustomer());
              assertEquals(orderResponseDTO.getDishes(), d.getDishes());
            });

    Mockito.verify(orderService).createOrder(orderRequestDTO);
  }

  @Test
  @DisplayName("Obtener una orden por id")
  void getOrderById() {

    CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
    customerResponseDTO.setCustomerId(1L);
    customerResponseDTO.setName("John");
    customerResponseDTO.setLastName("Doe");
    customerResponseDTO.setEmail("doe@example.com");
    customerResponseDTO.setPhone("+1 223-3343-332");

    DishResponseDTO dishResponseDTO1 = new DishResponseDTO();
    dishResponseDTO1.setDishId(1L);
    dishResponseDTO1.setName("Ceviche de camarones");
    dishResponseDTO1.setPrice(12000.0f);

    OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
    orderResponseDTO.setOrderId(1L);
    orderResponseDTO.setCustomer(customerResponseDTO);
    orderResponseDTO.setDishes(List.of(dishResponseDTO1));

    Mockito.when(orderService.getOrderById(1L)).thenReturn(orderResponseDTO);

    webTestClient
            .get()
            .uri("/orders/{id}", 1L)
            .exchange()
            .expectStatus().isOk()
            .expectBody(OrderResponseDTO.class)
            .value(d -> {
              assertEquals(orderResponseDTO.getOrderId(), d.getOrderId());
              assertEquals(orderResponseDTO.getCustomer(), d.getCustomer());
              assertEquals(orderResponseDTO.getDishes(), d.getDishes());
            });

    Mockito.verify(orderService).getOrderById(1L);
  }

  @Test
  @DisplayName("Actualizar una orden")
  void updateOrder() {

    CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
    customerResponseDTO.setCustomerId(1L);
    customerResponseDTO.setName("John");
    customerResponseDTO.setLastName("Doe");
    customerResponseDTO.setEmail("doe@example.com");
    customerResponseDTO.setPhone("+1 223-3343-332");

    DishResponseDTO dishResponseDTO1 = new DishResponseDTO();
    dishResponseDTO1.setDishId(1L);
    dishResponseDTO1.setName("Ceviche de camarones");
    dishResponseDTO1.setPrice(12000.0f);

    OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
    orderResponseDTO.setOrderId(1L);
    orderResponseDTO.setCustomer(customerResponseDTO);
    orderResponseDTO.setDishes(List.of(dishResponseDTO1));

    OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
    orderRequestDTO.setCustomerId(1L);
    orderRequestDTO.setDishIds(List.of(1L));

    Mockito.when(orderService.updateOrder(1L, orderRequestDTO)).thenReturn(orderResponseDTO);

    webTestClient
            .put()
            .uri("/orders/{id}", 1L)
            .bodyValue(orderRequestDTO)
            .exchange()
            .expectStatus().isOk()
            .expectBody(OrderResponseDTO.class)
            .value(d -> {
              assertEquals(orderResponseDTO.getOrderId(), d.getOrderId());
              assertEquals(orderResponseDTO.getCustomer(), d.getCustomer());
              assertEquals(orderResponseDTO.getDishes(), d.getDishes());
            });

    Mockito.verify(orderService).updateOrder(1L, orderRequestDTO);
  }

  @Test
  @DisplayName("Eliminar una orden")
  void deleteOrder() {

    doNothing().when(orderService).deleteOrder(1L);

    webTestClient
            .delete()
            .uri("/orders/{id}", 1L)
            .exchange()
            .expectStatus().isNoContent();

    Mockito.verify(orderService).deleteOrder(1L);
  }

  @Test
  @DisplayName("Listar todas las ordenes")
  void listOrders() {

    List<OrderResponseDTO> orderResponseDTOs = getOrderList();

    Mockito.when(orderService.listOrders()).thenReturn(orderResponseDTOs);

    webTestClient
            .get()
            .uri("/orders")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(OrderResponseDTO.class)
            .hasSize(2)
            .value(d -> {
              assertEquals(1L, d.get(0).getOrderId());
              assertEquals("John", d.get(0).getCustomer().getName());
              assertEquals("Brownie", d.get(0).getDishes().stream().toList().get(0).getName());
              assertEquals("Malteada de fresa", d.get(0).getDishes().stream().toList().get(1).getName());
              assertEquals(2L, d.get(1).getOrderId());
              assertEquals("Jane", d.get(1).getCustomer().getName());
              assertEquals("Tarta de limon", d.get(1).getDishes().stream().toList().get(0).getName());
              assertEquals("Malteada de fresa", d.get(1).getDishes().stream().toList().get(1).getName());
            });

    Mockito.verify(orderService).listOrders();

  }

  public List<DishResponseDTO> getDishList1() {
    DishResponseDTO dishResponseDTO1 = new DishResponseDTO();
    dishResponseDTO1.setDishId(1L);
    dishResponseDTO1.setName("Brownie");
    dishResponseDTO1.setPrice(10000.0f);

    DishResponseDTO dishResponseDTO2 = new DishResponseDTO();
    dishResponseDTO2.setDishId(2L);
    dishResponseDTO2.setName("Malteada de fresa");
    dishResponseDTO2.setPrice(20000.0f);

    return List.of(dishResponseDTO1, dishResponseDTO2);
  }

  public List<DishResponseDTO> getDishList2() {
    DishResponseDTO dishResponseDTO1 = new DishResponseDTO();
    dishResponseDTO1.setDishId(3L);
    dishResponseDTO1.setName("Tarta de limon");
    dishResponseDTO1.setPrice(30000.0f);

    DishResponseDTO dishResponseDTO2 = new DishResponseDTO();
    dishResponseDTO2.setDishId(2L);
    dishResponseDTO2.setName("Malteada de fresa");
    dishResponseDTO2.setPrice(20000.0f);

    return List.of(dishResponseDTO1, dishResponseDTO2);
  }

  public List<OrderResponseDTO> getOrderList() {
    OrderResponseDTO orderResponseDTO1 = new OrderResponseDTO();
    orderResponseDTO1.setOrderId(1L);
    orderResponseDTO1.setCustomer(
            new CustomerResponseDTO() {{
              setCustomerId(1L);
              setName("John");
              setLastName("Doe");
              setEmail("doe@example.com");
              setPhone("+1 223-3343-332");
            }}
    );
    orderResponseDTO1.setDishes(getDishList1());

    OrderResponseDTO orderResponseDTO2 = new OrderResponseDTO();
    orderResponseDTO2.setOrderId(2L);
    orderResponseDTO2.setCustomer(
            new CustomerResponseDTO() {{
              setCustomerId(2L);
              setName("Jane");
              setLastName("Doe");
              setEmail("doe@gmail.com");
              setPhone("+57 313-221-12-34");
            }}
    );
    orderResponseDTO2.setDishes(getDishList2());

    return List.of(orderResponseDTO1, orderResponseDTO2);
  }
}