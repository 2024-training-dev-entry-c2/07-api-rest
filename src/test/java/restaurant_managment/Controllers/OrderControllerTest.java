package restaurant_managment.Controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import restaurant_managment.Models.CustomerModel;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.OrderModel;
import restaurant_managment.Models.ReservationModel;
import restaurant_managment.Proxy.OrderServiceProxy;
import restaurant_managment.Repositories.DishRepository;
import restaurant_managment.Repositories.ReservationRepository;
import restaurant_managment.Services.OrderService;
import restaurant_managment.Utils.Dto.Order.OrderDTOConverter;
import restaurant_managment.Utils.Dto.Order.OrderRequestDTO;
import restaurant_managment.Utils.Dto.Order.OrderResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class OrderControllerTest {

  private final WebTestClient webTestClient;
  private final OrderServiceProxy orderServiceProxy;
  private final OrderService orderService;
  private final OrderDTOConverter orderDTOConverter;
  private final ReservationRepository reservationRepository;
  private final DishRepository dishRepository;

  OrderControllerTest() {
    dishRepository = mock(DishRepository.class);
    reservationRepository = mock(ReservationRepository.class);
    orderServiceProxy = mock(OrderServiceProxy.class);
    orderService = mock(OrderService.class);
    orderDTOConverter = new OrderDTOConverter(reservationRepository, dishRepository);
    webTestClient = WebTestClient.bindToController(new OrderController(orderService, orderServiceProxy, orderDTOConverter)).build();
  }

  @Test
  @DisplayName("Create order")
  void createOrder() {
    DishModel dish1 = new DishModel(5L, false, true, "Margherita Pizza", 9.99, "Simple pizza with fresh mozzarella, tomatoes, basil, and olive oil.");
    DishModel dish2 = new DishModel(6L, false, true, "Grilled Chicken Caesar Salad", 11.49, "Crispy romaine lettuce, grilled chicken, Caesar dressing, and croutons.");
    DishModel dish3 = new DishModel(7L, true, true, "Vegetable Stir Fry", 10.29, "Mixed vegetables stir-fried in soy sauce and served over steamed rice.");
    List<DishModel> dishes = List.of(dish1, dish2, dish3);

    CustomerModel customer = new CustomerModel(1L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890");
    ReservationModel reservation1 = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 2, "pending");

    OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
    orderRequestDTO.setReservationId(1L);
    orderRequestDTO.setDishIds(List.of(5L, 6L, 7L));
    orderRequestDTO.setStatus("pending");

    OrderModel order = new OrderModel(1L, reservation1, dishes, "pending", 0.0);

    when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation1));
    when(dishRepository.findById(5L)).thenReturn(Optional.of(dish1));
    when(dishRepository.findById(6L)).thenReturn(Optional.of(dish2));
    when(dishRepository.findById(7L)).thenReturn(Optional.of(dish3));
    when(orderService.createOrder(any(OrderModel.class))).thenReturn(order);

    webTestClient.post()
      .uri("/orders")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(orderRequestDTO)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(OrderResponseDTO.class)
      .value(orderResponse -> {
        assertEquals(order.getReservation().getId(), orderResponse.getReservationId());
        assertEquals(order.getStatus(), orderResponse.getStatus());
        assertEquals(order.getTotalPrice(), orderResponse.getTotalPrice());
      });

    verify(orderService).createOrder(any(OrderModel.class));
    verify(reservationRepository).findById(anyLong());
    verify(dishRepository, times(3)).findById(anyLong());
  }

  @Test
  @DisplayName("Get order by ID")
  void getOrderById() {
    DishModel dish1 = new DishModel(5L, false, true, "Margherita Pizza", 9.99, "Simple pizza with fresh mozzarella, tomatoes, basil, and olive oil.");
    List<DishModel> dishes = List.of(dish1);
    CustomerModel customer = new CustomerModel(1L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890");
    ReservationModel reservation1 = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 2, "pending");
    OrderModel order = new OrderModel(1L, reservation1, dishes, "pending", 0.0);

    when(orderServiceProxy.getOrderById(1L)).thenReturn(Optional.of(order));

    webTestClient.get()
      .uri("/orders/1")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(OrderResponseDTO.class)
      .value(orderResponse -> {
        assertEquals(order.getReservation().getId(), orderResponse.getReservationId());
        assertEquals(order.getStatus(), orderResponse.getStatus());
        assertEquals(order.getTotalPrice(), orderResponse.getTotalPrice());
      });

    verify(orderServiceProxy).getOrderById(1L);
  }

  @Test
  @DisplayName("Get all orders")
  void getAllOrders() {
    DishModel dish1 = new DishModel(5L, false, true, "Margherita Pizza", 9.99, "Simple pizza with fresh mozzarella, tomatoes, basil, and olive oil.");
    List<DishModel> dishes = List.of(dish1);
    CustomerModel customer = new CustomerModel(1L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890");
    ReservationModel reservation1 = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 2, "pending");
    OrderModel order1 = new OrderModel(1L, reservation1, dishes, "pending", 0.0);
    OrderModel order2 = new OrderModel(2L, reservation1, dishes, "pending", 0.0);
    List<OrderModel> orders = List.of(order1, order2);

    when(orderService.getAllOrders()).thenReturn(orders);

    webTestClient.get()
      .uri("/orders")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(OrderResponseDTO.class)
      .value(orderResponses -> {
        assertEquals(2, orderResponses.size());
        assertEquals(order1.getReservation().getId(), orderResponses.get(0).getReservationId());
        assertEquals(order2.getReservation().getId(), orderResponses.get(1).getReservationId());
      });

    verify(orderService).getAllOrders();
  }

  @Test
  @DisplayName("Update order")
  void updateOrder() {
    DishModel dish1 = new DishModel(5L, false, true, "Margherita Pizza", 9.99, "Simple pizza with fresh mozzarella, tomatoes, basil, and olive oil.");
    List<DishModel> dishes = List.of(dish1);
    CustomerModel customer = new CustomerModel(1L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890");
    ReservationModel reservation1 = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 2, "pending");

    when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation1));
    when(dishRepository.findById(5L)).thenReturn(Optional.of(dish1));

    OrderModel updatedOrder = new OrderModel(1L, reservation1, dishes, "completed", 9.99);
    OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
    orderRequestDTO.setReservationId(1L);
    orderRequestDTO.setDishIds(List.of(5L));
    orderRequestDTO.setStatus("completed");

    when(orderService.updateOrder(eq(1L), any(OrderModel.class))).thenReturn(updatedOrder);

    webTestClient.put()
      .uri("/orders/1")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(orderRequestDTO)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(OrderResponseDTO.class)
      .value(orderResponse -> {
        assertEquals(updatedOrder.getReservation().getId(), orderResponse.getReservationId());
        assertEquals(updatedOrder.getStatus(), orderResponse.getStatus());
        assertEquals(updatedOrder.getTotalPrice(), orderResponse.getTotalPrice());
      });

    verify(orderService).updateOrder(eq(1L), any(OrderModel.class));
    verify(reservationRepository).findById(anyLong());
    verify(dishRepository).findById(anyLong());
  }

  @Test
  @DisplayName("Delete order")
  void deleteOrder() {
    webTestClient.delete()
      .uri("/orders/1")
      .exchange()
      .expectStatus().isNoContent();

    verify(orderService).deleteOrder(1L);
  }
}