package com.restaurant.management.services;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Menu;
import com.restaurant.management.models.Order;
import com.restaurant.management.models.OrderDish;
import com.restaurant.management.observer.IOrderObserver;
import com.restaurant.management.repositories.ClientRepository;
import com.restaurant.management.repositories.DishRepository;
import com.restaurant.management.repositories.OrderDishRepository;
import com.restaurant.management.repositories.OrderRepository;
import com.restaurant.management.services.observer.ClientOrderObserver;
import com.restaurant.management.services.observer.DishOrderObserver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {
  private final OrderRepository orderRepository;
  private final OrderService orderService;
  private final ClientRepository clientRepository;
  private final DishRepository dishRepository;
  private final OrderDishRepository orderDishRepository;
  private final IOrderObserver clientObserver;
  private final IOrderObserver dishObserver;

  OrderServiceTest() {
    this.orderRepository = mock(OrderRepository.class);
    this.clientRepository = mock(ClientRepository.class);
    this.dishRepository = mock(DishRepository.class);
    this.orderDishRepository = mock(OrderDishRepository.class);
    this.clientObserver = mock(ClientOrderObserver.class);
    this.dishObserver = mock(DishOrderObserver.class);

    this.orderService = new OrderService(
      orderRepository,
      new ArrayList<>(List.of(clientObserver, dishObserver)),
      clientRepository,
      dishRepository,
      orderDishRepository
    );
  }

  @Test
  @DisplayName("Agregar pedido")
  void addOrder() {
    Client client = mock(Client.class);
    Dish dish = mock(Dish.class);
    Order order = new Order(1L, client, LocalDate.now());
    order.setOrderDishes(List.of(new OrderDish(dish, 1)));

    when(orderRepository.save(eq(order))).thenReturn(order);

    Order actualOrder = orderService.addOrder(order);

    assertEquals(order.getId(), actualOrder.getId());
    assertEquals(order.getClient().getId(), actualOrder.getClient().getId());
    assertEquals(order.getDate(), actualOrder.getDate());
    verify(clientObserver).updateOrder(eq(client), eq(dish));
    verify(dishObserver).updateOrder(eq(client), eq(dish));
    verify(orderRepository).save(order);
  }

  @Test
  @DisplayName("Obtener pedido por id")
  void getOrderById() {
    Client client = mock(Client.class);
    Dish dish = mock(Dish.class);
    Order order = new Order(1L, client, LocalDate.now());
    order.setOrderDishes(List.of(new OrderDish(dish, 1)));

    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

    Optional<Order> retrievedOrder = orderService.getOrderById(1L);

    assertTrue(retrievedOrder.isPresent());
    assertEquals(order.getId(), retrievedOrder.get().getId());
    verify(orderRepository).findById(anyLong());
  }

  @Test
  @DisplayName("Obtener lista de pedidos")
  void getOrders() {
    when(orderRepository.findAll()).thenReturn(getOrderList());

    List<Order> retrievedOrders = orderService.getOrders();

    assertEquals(getOrderList().size(), retrievedOrders.size());
    assertEquals(getOrderList().get(0).getId(), retrievedOrders.get(0).getId());
    assertEquals(getOrderList().get(1).getId(), retrievedOrders.get(1).getId());
    assertEquals(getOrderList().get(2).getId(), retrievedOrders.get(2).getId());

    verify(orderRepository).findAll();
  }

  @Test
  @DisplayName("Actualizar pedido exitoso")
  void updateOrder() {
    Client client = mock(Client.class);
    Menu menu = mock(Menu.class);
    Dish dish1 = new Dish(1L, "Pizza", "", 10f, menu);
    Dish dish2 = new Dish(2L, "Pasta", "", 12f, menu);

    Order order = new Order(1L, client, LocalDate.now());
    order.setOrderDishes(new ArrayList<>(List.of(new OrderDish(dish1, 1))));

    Order updatedOrder = new Order(1L, client, LocalDate.now());
    updatedOrder.setOrderDishes(new ArrayList<>(List.of(
      new OrderDish(dish2, 1),
      new OrderDish(dish1, 2))));

    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(orderRepository.save(eq(order))).thenAnswer(invocation -> invocation.getArgument(0));

    Order result = orderService.updateOrder(1L, updatedOrder);

    assertEquals(order.getId(), result.getId());
    assertEquals(order.getClient().getId(), result.getClient().getId());
    assertEquals(order.getDate(), result.getDate());

    assertEquals(updatedOrder.getOrderDishes().get(0).getDish().getId(), result.getOrderDishes().get(0).getDish().getId());
    assertEquals(updatedOrder.getOrderDishes().get(1).getDish().getId(), result.getOrderDishes().get(1).getDish().getId());

    verify(orderRepository).findById(anyLong());
    verify(orderRepository).save(order);
  }

  @Test
  @DisplayName("Eliminar pedido exitoso")
  void deleteOrder() {
    doNothing().when(orderRepository).deleteById(anyLong());

    orderService.deleteOrder(1L);

    verify(orderRepository).deleteById(anyLong());
  }

  @Test
  @DisplayName("Aplicar descuento cliente normal")
  void applyDiscountsNormalClient() {
    Client client = new Client(1L, "name", "email");
    Order order = new Order(1L, client, LocalDate.now());
    order.setOrderDishes(new ArrayList<>(List.of(new OrderDish(mock(Dish.class), 1))));
    order.setTotal(100f);

    orderService.applyDiscounts(order);

    assertEquals(100f, order.getTotal());
  }

  @Test
  @DisplayName("Aplicar descuento cliente frecuente")
  void applyDiscountsFrequentClient() {
    Client client = new Client(1L, "name", "email");
    client.setFrequent(true);
    Order order = new Order(1L, client, LocalDate.now());
    order.setOrderDishes(new ArrayList<>(List.of(new OrderDish(mock(Dish.class), 1))));
    order.setTotal(100f);

    orderService.applyDiscounts(order);

    assertEquals(97.62f, order.getTotal());
  }

  @Test
  void addObserver() {
    orderService.addObserver(clientObserver);

    assertTrue(orderService.getObservers().contains(clientObserver));
  }

  @Test
  void removeObserver() {
    orderService.removeObserver(clientObserver);

    assertFalse(orderService.getObservers().contains(clientObserver));
  }

  @Test
  void notifyObservers() {
    Client client = mock(Client.class);
    Dish dish = mock(Dish.class);
    Order order = new Order(1L, client, LocalDate.now());
    order.setOrderDishes(new ArrayList<>(List.of(new OrderDish(dish, 1))));
    order.setTotal(100f);

    orderService.notifyObservers(order);

    verify(clientObserver).updateOrder(eq(client), eq(dish));
    verify(dishObserver).updateOrder(eq(client), eq(dish));
  }

  private List<Order> getOrderList(){
    return List.of(new Order(1L, new Client(1L, "name1", "email1"), LocalDate.parse("2025-01-08")),
      new Order(2L, new Client(2L, "name2", "email2"), LocalDate.parse("2025-01-08")),
      new Order(3L, new Client(3L, "name3", "email3"), LocalDate.parse("2025-01-08")));
  }
}