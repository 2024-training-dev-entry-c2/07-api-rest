package com.restaurant.restaurant_management.services.chainOfResponsibility;

import com.restaurant.restaurant_management.constants.AppConstants;
import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.models.ClientOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FrequentClientDiscountHandlerTest {
  private FrequentClientDiscountHandler discountHandler;
  private PriceHandler nextHandler;

  @BeforeEach
  void setUp() {
    discountHandler = new FrequentClientDiscountHandler();
    nextHandler = mock(PriceHandler.class);
    discountHandler.setNextHandler(nextHandler);
  }

  @Test
  @DisplayName("Calculate Total For Frequent Client")
  void testCalculateTotalForFrequentClient() {
    Client frequentClient = new Client();
    frequentClient.setIsFrequent(true);

    ClientOrder order = new ClientOrder();
    order.setClient(frequentClient);
    Double initialTotal = 24000.0;

    when(nextHandler.calculateTotal(order, initialTotal * AppConstants.DISCOUNT)).thenReturn(initialTotal * AppConstants.DISCOUNT);
    Double result = discountHandler.calculateTotal(order, initialTotal);

    assertEquals(initialTotal * AppConstants.DISCOUNT, result);

    Mockito.verify(nextHandler, Mockito.times(1)).calculateTotal(order, initialTotal * AppConstants.DISCOUNT);
  }

  @Test
  @DisplayName("Calculate Total For Non Frequent Client")
  void testCalculateTotalForNonFrequentClient() {
    Client nonFrequentClient = new Client();
    nonFrequentClient.setIsFrequent(false);

    ClientOrder order = new ClientOrder();
    order.setClient(nonFrequentClient);
    Double initialTotal = 24000.0;

    when(nextHandler.calculateTotal(order, initialTotal)).thenReturn(initialTotal);
    Double result = discountHandler.calculateTotal(order, initialTotal);

    assertEquals(initialTotal, result);

    Mockito.verify(nextHandler, Mockito.times(1)).calculateTotal(order, initialTotal);
  }

  @Test
  @DisplayName("Calculate Total Without Next Handler")
  void testCalculateTotalWithoutNextHandler() {
    Client frequentClient = new Client();
    frequentClient.setIsFrequent(true);
    ClientOrder order = new ClientOrder();
    order.setClient(frequentClient);

    discountHandler.setNextHandler(null);
    Double initialTotal = 24000.0;

    Double result = discountHandler.calculateTotal(order, initialTotal);

    assertEquals(initialTotal * AppConstants.DISCOUNT, result);
  }
}