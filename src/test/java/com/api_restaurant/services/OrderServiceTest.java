package com.api_restaurant.services;

import com.api_restaurant.models.Client;
import com.api_restaurant.models.Dish;
import com.api_restaurant.models.Order;
import com.api_restaurant.repositories.OrderRepository;
import com.api_restaurant.strategy.*;
import com.api_restaurant.utils.FrequentClientHandler;
import com.api_restaurant.utils.PopularDishHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private FrequentClientHandler frequentClientHandler;
    private DiscountStrategy noDiscountStrategy;
    private DiscountStrategy frequentClientDiscountStrategy;
    private PopularDishHandler popularDishHandler;
    private PriceStrategy regularPriceStrategy;
    private PriceStrategy popularDishPriceStrategy;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        frequentClientHandler = mock(FrequentClientHandler.class);
        noDiscountStrategy = mock(NoDiscountStrategy.class);
        frequentClientDiscountStrategy = mock(FrequentClientDiscountStrategy.class);
        popularDishHandler = mock(PopularDishHandler.class);
        regularPriceStrategy = mock(RegularPriceStrategy.class);
        popularDishPriceStrategy = mock(PopularDishPriceStrategy.class);


        orderService = new OrderService(
                orderRepository,
                frequentClientHandler,
                (NoDiscountStrategy) noDiscountStrategy,
                (FrequentClientDiscountStrategy) frequentClientDiscountStrategy,
                popularDishHandler,
                (RegularPriceStrategy) regularPriceStrategy,
                (PopularDishPriceStrategy) popularDishPriceStrategy
        );
    }


    @Test
    @DisplayName("Add Order - Success")
    void addOrderSuccess() {
        Client client = new Client();
        client.setFrequent(true);
        Dish dish1 = new Dish("Dish 1", "Description 1", 10.0, null);
        Dish dish2 = new Dish("Dish 2", "Description 2", 15.0, null);
        Order order = new Order(client, List.of(dish1, dish2), null);

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(frequentClientDiscountStrategy.applyDiscount(any(Double.class))).thenReturn(22.5);
        when(regularPriceStrategy.applyPrice(any(Double.class))).thenReturn(10.0, 15.0);

        Order result = orderService.addOrder(order);

        assertNotNull(result);
        assertEquals(22.5, result.getTotal());

        Mockito.verify(orderRepository).save(any(Order.class));
        Mockito.verify(frequentClientHandler).handle(client);
        Mockito.verify(popularDishHandler, times(2)).handle(any(Dish.class));
        Mockito.verify(frequentClientDiscountStrategy).applyDiscount(any(Double.class));
        Mockito.verify(regularPriceStrategy, times(2)).applyPrice(any(Double.class));
    }

    @Test
    @DisplayName("Add Order - Client Not Found")
    void addOrderClientNotFound() {
        Client client = new Client();
        Dish dish1 = new Dish("Dish 1", "Description 1", 10.0, null);
        Order order = new Order(client, List.of(dish1), null);

        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException("Client not found"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.addOrder(order);
        });

        assertEquals("Client not found", exception.getMessage());

        Mockito.verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Add Order - Dish Not Found")
    void addOrderDishNotFound() {
        Client client = new Client();
        Dish dish1 = new Dish("Dish 1", "Description 1", 10.0, null);
        Order order = new Order(client, List.of(dish1), null);

        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException("Dish not found"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.addOrder(order);
        });

        assertEquals("Dish not found", exception.getMessage());

        Mockito.verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Add Order - No Discount Strategy")
    void addOrderNoDiscountStrategy() {
        Client client = new Client();
        client.setFrequent(false);
        Dish dish1 = new Dish("Dish 1", "Description 1", 10.0, null);
        Dish dish2 = new Dish("Dish 2", "Description 2", 15.0, null);
        Order order = new Order(client, List.of(dish1, dish2), null);

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(noDiscountStrategy.applyDiscount(any(Double.class))).thenReturn(25.0);
        when(regularPriceStrategy.applyPrice(any(Double.class))).thenReturn(10.0, 15.0);

        Order result = orderService.addOrder(order);

        assertNotNull(result);
        assertEquals(25.0, result.getTotal());

        Mockito.verify(orderRepository).save(any(Order.class));
        Mockito.verify(frequentClientHandler).handle(client);
        Mockito.verify(popularDishHandler, times(2)).handle(any(Dish.class));
        Mockito.verify(noDiscountStrategy).applyDiscount(any(Double.class));
        Mockito.verify(regularPriceStrategy, times(2)).applyPrice(any(Double.class));
    }

@Test
@DisplayName("Add Order - Popular Dish Price Strategy")
void addOrderPopularDishPriceStrategy() {
    Client client = new Client();
    client.setFrequent(true);
    Dish dish1 = new Dish("Dish 1", "Description 1", 10.0, null);
    dish1.setSpecialDish(true);
    Dish dish2 = new Dish("Dish 2", "Description 2", 15.0, null);
    Order order = new Order(client, List.of(dish1, dish2), null);

    when(orderRepository.save(any(Order.class))).thenReturn(order);
    when(frequentClientDiscountStrategy.applyDiscount(any(Double.class))).thenReturn(26.775);
    when(popularDishPriceStrategy.applyPrice(any(Double.class))).thenReturn(10.573);
    when(regularPriceStrategy.applyPrice(any(Double.class))).thenReturn(15.0);

    Order result = orderService.addOrder(order);

    assertNotNull(result);
    assertEquals(26.775, result.getTotal());

    Mockito.verify(orderRepository).save(any(Order.class));
    Mockito.verify(frequentClientHandler).handle(client);
    Mockito.verify(popularDishHandler, times(2)).handle(any(Dish.class));
    Mockito.verify(frequentClientDiscountStrategy).applyDiscount(any(Double.class));
    Mockito.verify(popularDishPriceStrategy).applyPrice(any(Double.class));
    Mockito.verify(regularPriceStrategy).applyPrice(any(Double.class));
}

    @Test
    @DisplayName("Get Order - Success")
    void getOrderSuccess() {
        Client client = new Client();
        Dish dish1 = new Dish("Dish 1", "Description 1", 10.0, null);
        Order order = new Order(client, List.of(dish1), 10.0);
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrder(1L);

        assertTrue(result.isPresent());
        assertEquals(order.getId(), result.get().getId());
        assertEquals(order.getClient(), result.get().getClient());
        assertEquals(order.getDishes(), result.get().getDishes());
        assertEquals(order.getTotal(), result.get().getTotal());

        Mockito.verify(orderRepository).findById(1L);
    }

    @Test
    @DisplayName("Get Order - Not Found")
    void getOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrder(1L);

        assertTrue(result.isEmpty());

        Mockito.verify(orderRepository).findById(1L);
    }

    @Test
    @DisplayName("Get Orders - Success")
    void getOrdersSuccess() {
        Client client = new Client();
        Dish dish1 = new Dish("Dish 1", "Description 1", 10.0, null);
        Dish dish2 = new Dish("Dish 2", "Description 2", 15.0, null);
        Order order1 = new Order(client, List.of(dish1), 10.0);
        Order order2 = new Order(client, List.of(dish2), 15.0);

        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        List<Order> result = orderService.getOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(order1.getId(), result.get(0).getId());
        assertEquals(order2.getId(), result.get(1).getId());

        Mockito.verify(orderRepository).findAll();
    }

    @Test
    @DisplayName("Get Orders - No Orders Found")
    void getOrdersNotFound() {
        when(orderRepository.findAll()).thenReturn(List.of());

        List<Order> result = orderService.getOrders();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(orderRepository).findAll();
    }

    @Test
    @DisplayName("Update Order - Success")
    void updateOrderSuccess() {
        Client client = new Client();
        Dish dish1 = new Dish("Dish 1", "Description 1", 10.0, null);
        Order existingOrder = new Order(client, List.of(dish1), 10.0);
        existingOrder.setId(1L);

        Dish dish2 = new Dish("Dish 2", "Description 2", 15.0, null);
        Order updatedOrder = new Order(client, List.of(dish2), 15.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        Order result = orderService.updateOrder(1L, updatedOrder);

        assertNotNull(result);
        assertEquals(updatedOrder.getDishes(), result.getDishes());
        assertEquals(updatedOrder.getTotal(), result.getTotal());

        Mockito.verify(orderRepository).findById(1L);
        Mockito.verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Update Order - Not Found")
    void updateOrderNotFound() {
        Order updatedOrder = new Order();

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.updateOrder(1L, updatedOrder);
        });

        assertEquals("Order with id 1 could not be updated", exception.getMessage());

        Mockito.verify(orderRepository).findById(1L);
        Mockito.verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    @DisplayName("Delete Order - Success")
    void deleteOrderSuccess() {
        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        Mockito.verify(orderRepository).findById(1L);
        Mockito.verify(orderRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Delete Order - Not Found")
    void deleteOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.deleteOrder(1L);
        });

        assertEquals("Order with id 1 not found", exception.getMessage());

        Mockito.verify(orderRepository).findById(1L);
        Mockito.verify(orderRepository, never()).deleteById(1L);
    }
}