package restaurant_managment.Proxy;

import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.OrderModel;
import restaurant_managment.interfaces.IOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceProxyTest {

  @Mock
  private IOrderService orderService;

  @InjectMocks
  private OrderServiceProxy orderServiceProxy;

  private OrderModel order;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 15.0, "Description 2");
    order = new OrderModel(1L, null, Arrays.asList(dish1, dish2), "Pending", null);
  }

  @Test
  void testGetOrderById_OrderNotInCache() {
    when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

    Optional<OrderModel> result = orderServiceProxy.getOrderById(1L);

    assertTrue(result.isPresent());
    assertEquals(order, result.get());

    verify(orderService, times(1)).getOrderById(1L);
  }

  @Test
  void testGetOrderById_OrderInCache() {
    // Llama una vez para llenar la cache
    when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));
    orderServiceProxy.getOrderById(1L);

    // Llama de nuevo para verificar que se use la cache
    Optional<OrderModel> result = orderServiceProxy.getOrderById(1L);

    assertTrue(result.isPresent());
    assertEquals(order, result.get());

    // Verificar que el método del servicio real sólo se llama una vez
    verify(orderService, times(1)).getOrderById(1L);
  }
}