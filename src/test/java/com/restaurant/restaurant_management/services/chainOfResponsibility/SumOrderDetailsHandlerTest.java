package com.restaurant.restaurant_management.services.chainOfResponsibility;

import com.restaurant.restaurant_management.models.ClientOrder;
import com.restaurant.restaurant_management.models.OrderDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SumOrderDetailsHandlerTest {
  private SumOrderDetailsHandler sumOrderDetailsHandler;
  private PriceHandler nextHandler;

  @BeforeEach
  void setUp() {
    sumOrderDetailsHandler = new SumOrderDetailsHandler();
    nextHandler = mock(PriceHandler.class);
    sumOrderDetailsHandler.setNextHandler(nextHandler);
  }

  @Test
  @DisplayName("Calculate Total With Order Details")
  void testCalculateTotalWithOrderDetails() {
    OrderDetail detail1 = new OrderDetail();
    detail1.setSubtotal(45000.0);
    OrderDetail detail2 = new OrderDetail();
    detail2.setSubtotal(25000.0);

    ClientOrder order = new ClientOrder();
    order.setOrderDetails(List.of(detail1, detail2));
    Double initialTotal = 20000.0;

    when(nextHandler.calculateTotal(order, initialTotal + 45000.0 + 25000.0)).thenReturn(initialTotal + 45000.0 + 25000.0);
    Double result = sumOrderDetailsHandler.calculateTotal(order, initialTotal);

    assertEquals(initialTotal + 45000.0 + 25000.0, result);
    Mockito.verify(nextHandler, Mockito.times(1)).calculateTotal(order, initialTotal + 45000.0 + 25000.0);
  }

  @Test
  @DisplayName("Calculate Total Without Order Details")
  void testCalculateTotalWithoutOrderDetails() {
    ClientOrder order = new ClientOrder();
    order.setOrderDetails(Collections.emptyList());
    Double initialTotal = 20000.0;

    when(nextHandler.calculateTotal(order, initialTotal)).thenReturn(initialTotal);
    Double result = sumOrderDetailsHandler.calculateTotal(order, initialTotal);

    assertEquals(initialTotal, result);
    Mockito.verify(nextHandler, Mockito.times(1)).calculateTotal(order, initialTotal);
  }

  @Test
  @DisplayName("Calculate Total Without Next Handler")
  void testCalculateTotalWithoutNextHandler() {
    OrderDetail detail1 = new OrderDetail();
    detail1.setSubtotal(45000.0);
    OrderDetail detail2 = new OrderDetail();
    detail2.setSubtotal(25000.0);

    ClientOrder order = new ClientOrder();
    order.setOrderDetails(List.of(detail1, detail2));

    sumOrderDetailsHandler.setNextHandler(null);
    Double initialTotal = 20000.0;

    Double result = sumOrderDetailsHandler.calculateTotal(order, initialTotal);

    assertEquals(initialTotal + 45000.0 + 25000.0, result);
  }
}