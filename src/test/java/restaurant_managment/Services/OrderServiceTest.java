package restaurant_managment.Services;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import restaurant_managment.Models.CustomerModel;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.OrderModel;
import restaurant_managment.Models.ReservationModel;
import restaurant_managment.Observer.Subject;
import restaurant_managment.Repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class OrderServiceTest {

  private final OrderRepository orderRepository = mock(OrderRepository.class);
  private final EntityManager entityManager = mock(EntityManager.class);
  private final Subject subject = mock(Subject.class);
  private final OrderService orderService = new OrderService(orderRepository, entityManager);

  public OrderServiceTest() {
    orderService.subject = subject;
  }

  @Test
  @DisplayName("Get all orders")
  void getAllOrders() {
    OrderModel order1 = new OrderModel(1L, null, null, "pending", 100.0);
    OrderModel order2 = new OrderModel(2L, null, null, "completed", 200.0);
    List<OrderModel> orders = List.of(order1, order2);

    when(orderRepository.findAll()).thenReturn(orders);

    List<OrderModel> result = orderService.getAllOrders();
    assertEquals(2, result.size());
    assertEquals(orders, result);

    verify(orderRepository).findAll();
  }

  @Test
  @DisplayName("Get order by ID")
  void getOrderById() {
    OrderModel order = new OrderModel(1L, null, null, "pending", 100.0);

    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

    Optional<OrderModel> result = orderService.getOrderById(1L);
    assertEquals(Optional.of(order), result);

    verify(orderRepository).findById(anyLong());
  }

  @Test
  @DisplayName("Create order")
  void createOrder() {
    CustomerModel customer = new CustomerModel(1L, true, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);
    OrderModel order = new OrderModel(1L, reservation, dishes, "pending", 0.0);
    OrderModel savedOrder = new OrderModel(1L, reservation, dishes, "pending", 30.0);

    when(orderRepository.save(any(OrderModel.class))).thenReturn(savedOrder);

    OrderModel result = orderService.createOrder(order);
    assertEquals(savedOrder, result);
    assertEquals(30.0, result.getTotalPrice());

    verify(orderRepository).save(any(OrderModel.class));
    verify(subject).notifyObservers();
  }

  @Test
  @DisplayName("Update order")
  void updateOrder() {
    CustomerModel customer = new CustomerModel(1L, true, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);
    OrderModel order = new OrderModel(1L, reservation, dishes, "pending", 0.0);
    OrderModel updatedOrder = new OrderModel(1L, reservation, dishes, "completed", 30.0);

    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(orderRepository.save(any(OrderModel.class))).thenReturn(updatedOrder);

    OrderModel result = orderService.updateOrder(1L, updatedOrder);
    assertEquals(updatedOrder, result);
    assertEquals(30.0, result.getTotalPrice());

    verify(orderRepository).findById(anyLong());
    verify(orderRepository).save(any(OrderModel.class));
    verify(subject).notifyObservers();
  }

  @Test
  @DisplayName("Update order - not found")
  void updateOrderNotFound() {
    OrderModel updatedOrder = new OrderModel(1L, null, null, "completed", 30.0);

    when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      orderService.updateOrder(1L, updatedOrder);
    });

    assertEquals("Order not found", exception.getMessage());

    verify(orderRepository).findById(anyLong());
    verify(orderRepository, never()).save(any(OrderModel.class));
    verify(subject, never()).notifyObservers();
  }

  @Test
  @DisplayName("Delete order")
  void deleteOrder() {
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    OrderModel order = new OrderModel(1L, reservation, null, "pending", 100.0);

    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    doNothing().when(orderRepository).deleteById(anyLong());

    orderService.deleteOrder(1L);

    verify(orderRepository).findById(anyLong());
    verify(orderRepository).deleteById(anyLong());
  }

  @Test
  @DisplayName("Calculate total price")
  void calculateTotalPrice() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    OrderModel order = getOrderModel(dish1);

    Double totalPrice = orderService.calculateTotalPrice(order);

    double expectedPrice = 30.0 * 0.9762;
    expectedPrice += 10.0 * 0.0573;
    expectedPrice += 20.0 * 0.0573;

    expectedPrice = Math.round(expectedPrice * 1000.0) / 1000.0;
    totalPrice = Math.round(totalPrice * 1000.0) / 1000.0;

    assertEquals(expectedPrice, totalPrice, 0.001);
  }

  private static OrderModel getOrderModel(DishModel dish1) {
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);
    CustomerModel customer = new CustomerModel(1L, true, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    OrderModel order = new OrderModel(1L, reservation, dishes, "pending", 0.0);

    order.setTotalPrice(dish1.getPrice() + dish2.getPrice()); // 30.0
    return order;
  }
}