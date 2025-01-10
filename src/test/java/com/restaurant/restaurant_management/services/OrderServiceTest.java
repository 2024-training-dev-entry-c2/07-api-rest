package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.constants.OrderStatus;
import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.models.ClientOrder;
import com.restaurant.restaurant_management.repositories.ClientRepository;
import com.restaurant.restaurant_management.repositories.OrderRepository;
import com.restaurant.restaurant_management.services.chainOfResponsibility.PriceHandler;
import com.restaurant.restaurant_management.services.observer.EventManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class OrderServiceTest {
  private OrderRepository orderRepository;
  private ClientRepository clientRepository;
  private EventManager eventManager;
  private PriceHandler priceHandlerChain;
  private OrderService orderService;

  private Client client;
  private ClientOrder order;
  private ClientOrder newOrder;
  private ClientOrder order2;
  private List<ClientOrder> orders;
  private ClientOrder updatedOrder;
  private ClientOrder savedOrder;

  @BeforeEach
  void setUp() {
    orderRepository = mock(OrderRepository.class);
    clientRepository = mock(ClientRepository.class);
    eventManager = spy(new EventManager("NewOrder", "DishOrdered"));
    priceHandlerChain = mock(PriceHandler.class);

    doNothing().when(eventManager).notify(any(String.class), any(Object.class));

    orderService = new OrderService(orderRepository, clientRepository);
    orderService.setPriceHandlerChain(priceHandlerChain);

    client = new Client(1L,"Juan","Perez","juanperez@gmail.com","123456789","Av. de la Independencia, 1",true);
    newOrder = new ClientOrder(null, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING,90000.0,client);
    order = new ClientOrder(1L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING,90000.0,client);
    order2 = new ClientOrder(2L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING,135000.0,client);
    orders = List.of(order, order2);
    savedOrder = new ClientOrder(1L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.COMPLETED,45000.0,client);
    updatedOrder = new ClientOrder(null, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.COMPLETED,45000.0,client);
  }

  @Test
  @DisplayName("Save Order")
  void saveOrder() {
    when(orderRepository.saveAndFlush(newOrder)).thenReturn(order);
    ClientOrder result = orderService.saveOrder(newOrder);
    assertNotNull(result);
    assertEquals(order.getId(), result.getId());
    Mockito.verify(orderRepository, times(1)).saveAndFlush(newOrder);
  }

  @Test
  @DisplayName("Get Order")
  void getOrder() {
    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    Optional<ClientOrder> result = orderService.getOrder(1L);
    assertTrue(result.isPresent());
    assertEquals(order.getId(), result.get().getId());
    Mockito.verify(orderRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Find Orders By Date")
  void findOrdersByDate() {
    when(orderRepository.findByOrderDateTimeBetween(any(LocalDateTime.class),any(LocalDateTime.class) )).thenReturn(orders);
    List<ClientOrder> result = orderService.findOrdersByDate(LocalDate.of(2025, 1, 7));
    assertNotNull(result);
    assertEquals(orders.size(), result.size());
    Mockito.verify(orderRepository, times(1)).findByOrderDateTimeBetween(any(LocalDateTime.class),any(LocalDateTime.class) );
  }

  @Test
  @DisplayName("Update Order")
  void updateOrder() {
    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    when(orderRepository.save(any(ClientOrder.class))).thenReturn(savedOrder);

    ClientOrder result = orderService.updateOrder(1L, updatedOrder);

    assertEquals(savedOrder.getId(), result.getId());
    assertEquals(OrderStatus.COMPLETED, result.getStatus());
    assertEquals(45000.0, result.getTotal());

    Mockito.verify(orderRepository, times(1)).findById(1L);
    Mockito.verify(orderRepository, times(1)).save(any(ClientOrder.class));
  }

  @Test
  @DisplayName("Update Order - Not Found")
  void updateOrder_notFound() {
    when(orderRepository.findById(1L)).thenReturn(Optional.empty());
    RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.updateOrder(1L, new ClientOrder()));
    assertEquals("Pedido con el id 1 no pudo ser actualizado", exception.getMessage());
    Mockito.verify(orderRepository, times(1)).findById(1L);
    Mockito.verify(orderRepository, Mockito.never()).save(any(ClientOrder.class));
  }

  @Test
  @DisplayName("Delete Order")
  void deleteOrder() {
    doNothing().when(orderRepository).deleteById(1L);
    orderService.deleteOrder(1L);
    Mockito.verify(orderRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Update Total Order")
  void updateTotalOrder() {
    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    when(priceHandlerChain.calculateTotal(order, 0.0)).thenReturn(90000.0);

    orderService.updateTotalOrder(1L);

    assertEquals(90000.0, order.getTotal());
    Mockito.verify(priceHandlerChain, times(1)).calculateTotal(order, 0.0);
    Mockito.verify(orderRepository, times(1)).save(order);
  }
}