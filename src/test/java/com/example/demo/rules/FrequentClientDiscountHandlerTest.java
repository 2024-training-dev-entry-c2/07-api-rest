//package com.example.demo.rules;
//
//import com.example.demo.models.Client;
//import com.example.demo.models.Order;
//import com.example.demo.strategy.PricingStrategy;
//import org.junit.jupiter.api.Test;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//class FrequentClientDiscountHandlerTest {
//    @Test
//    void applyRule_ShouldApplyStrategyAndPassToNextHandler() {
//        PricingStrategy strategy = mock(PricingStrategy.class);
//
//        OrderRule nextHandler = mock(OrderRule.class);
//
//        FrequentClientDiscountHandler handler = new FrequentClientDiscountHandler(strategy);
//
//        handler.setNextHandler(nextHandler);
//
//        Client client = new Client();
//        client.setId(1L);
//        client.setName("Mario");
//        client.setIsOften(true);
//
//        Order order = new Order();
//        order.setId(1L);
//        order.setClient(client);
//        order.setTotalPrice(100.0);
//
//
//        handler.applyRule(order);
//
//        verify(strategy, times(1)).apply(order);
//
//        verify(nextHandler, times(1)).applyRule(order);
//    }
//    @Test
//    void applyRule_ShouldApplyStrategyWithoutNextHandler() {
//
//        PricingStrategy strategy = mock(PricingStrategy.class);
//
//        FrequentClientDiscountHandler handler = new FrequentClientDiscountHandler(strategy);
//
//        Client client = new Client();
//        client.setId(1L);
//        client.setName("Mario");
//        client.setIsOften(true);
//
//        Order order = new Order();
//        order.setId(1L);
//        order.setClient(client);
//        order.setTotalPrice(100.0);
//
//        handler.applyRule(order);
//
//        verify(strategy, times(1)).apply(order);
//
//
//    }
//
//}