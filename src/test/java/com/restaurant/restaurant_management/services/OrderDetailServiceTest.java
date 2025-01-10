package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.constants.OrderStatus;
import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.models.ClientOrder;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.models.Menu;
import com.restaurant.restaurant_management.models.OrderDetail;
import com.restaurant.restaurant_management.repositories.DishRepository;
import com.restaurant.restaurant_management.repositories.OrderDetailRepository;
import com.restaurant.restaurant_management.services.observer.EventManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

class OrderDetailServiceTest {
  private OrderDetailRepository orderDetailRepository;
  private DishRepository dishRepository;
  private EventManager eventManager;
  private OrderDetailService orderDetailService;

  private Menu menu;
  private Dish dish;
  private Client client;
  private ClientOrder order;
  private OrderDetail orderDetail;
  private OrderDetail newOrderDetail;
  private OrderDetail orderDetail2;
  private List<OrderDetail> orderDetailList;
  private OrderDetail updatedOrderDetail;
  private OrderDetail savedOrderDetail;

  @BeforeEach
  void setUp() {
    orderDetailRepository = mock(OrderDetailRepository.class);
    dishRepository = mock(DishRepository.class);
    eventManager = spy(new EventManager("NewOrder", "DishOrdered"));
    doNothing().when(eventManager).notify(any(String.class), any(Object.class));
    orderDetailService = new OrderDetailService(orderDetailRepository, dishRepository);

    menu = new Menu(1, "Menu 1", "Descripcion del menu 1", true);
    dish = new Dish(1, "Pollo", "Ocho presas", 45000, true, true, menu);
    client = new Client(1L,"Juan","Perez","juanperez@gmail.com","123456789","Av. de la Independencia, 1",true);
    order = new ClientOrder(1L, LocalDateTime.of(2025, 1, 7, 10,0), OrderStatus.PROCESSING,90000.0,client);
    newOrderDetail = new OrderDetail(null,2,90000.0,order,dish);
    orderDetail = new OrderDetail(1L,2,90000.0,order,dish);
    orderDetail2 = new OrderDetail(2L,1,45000.0,order,dish);
    orderDetailList = List.of(orderDetail, orderDetail2);
    updatedOrderDetail = new OrderDetail(null,1,45000.0,order,dish);
    savedOrderDetail = new OrderDetail(1L,1,45000.0,order,dish);
  }

  @Test
  @DisplayName("Save Order Detail")
  void saveOrderDetail() {
    when(orderDetailRepository.save(newOrderDetail)).thenReturn(orderDetail);
    OrderDetail result = orderDetailService.saveOrderDetail(newOrderDetail);
    assertNotNull(result);
    assertEquals(orderDetail.getId(), result.getId());
    Mockito.verify(orderDetailRepository, times(1)).save(newOrderDetail);
  }

  @Test
  @DisplayName("Get Order Detail")
  void getOrderDetail() {
    when(orderDetailRepository.findById(1L)).thenReturn(Optional.of(orderDetail));
    Optional<OrderDetail> result = orderDetailService.getOrderDetail(1L);
    assertTrue(result.isPresent());
    assertEquals(orderDetail.getId(), result.get().getId());
    Mockito.verify(orderDetailRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Save Order Details")
  void saveOrderDetails() {
    when(orderDetailRepository.saveAllAndFlush(orderDetailList)).thenReturn(orderDetailList);

    List<OrderDetail> result = orderDetailService.saveOrderDetails(orderDetailList);

    assertEquals(2, result.size());
    Mockito.verify(orderDetailRepository, times(1)).saveAllAndFlush(orderDetailList);
  }

  @Test
  @DisplayName("List Order Details By Order Id")
  void listOrderDetailsByOrderId() {
    when(orderDetailRepository.findByOrderId(1L)).thenReturn(orderDetailList);
    List<OrderDetail> result = orderDetailService.listOrderDetailsByOrderId(1L);
    assertNotNull(result);
    assertEquals(orderDetailList.size(), result.size());
    Mockito.verify(orderDetailRepository, times(1)).findByOrderId(1L);
  }

  @Test
  @DisplayName("Update Order Detail")
  void updateOrderDetail() {
    when(orderDetailRepository.findById(1L)).thenReturn(Optional.of(orderDetail));
    when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(savedOrderDetail);

    OrderDetail result = orderDetailService.updateOrderDetail(1L, updatedOrderDetail);

    assertEquals(savedOrderDetail.getId(), result.getId());
    assertEquals(1, result.getQuantity());
    assertEquals(45000.0, result.getSubtotal());

    Mockito.verify(orderDetailRepository, times(1)).findById(1L);
    Mockito.verify(orderDetailRepository, times(1)).save(any(OrderDetail.class));
  }

  @Test
  @DisplayName("Update Order Detail - Not Found")
  void updateOrderDetail_notFound() {
    when(orderDetailRepository.findById(1L)).thenReturn(Optional.empty());
    RuntimeException exception = assertThrows(RuntimeException.class, () -> orderDetailService.updateOrderDetail(1L, new OrderDetail()));
    assertEquals("Detalle de pedido con el id 1 no pudo ser actualizado", exception.getMessage());
    Mockito.verify(orderDetailRepository, times(1)).findById(1L);
    Mockito.verify(orderDetailRepository, Mockito.never()).save(any(OrderDetail.class));
  }

  @Test
  @DisplayName("Delete Order Detail")
  void deleteOrderDetail() {
    doNothing().when(orderDetailRepository).deleteById(1L);
    orderDetailService.deleteOrderDetail(1L);
    Mockito.verify(orderDetailRepository, times(1)).deleteById(1L);
  }
}