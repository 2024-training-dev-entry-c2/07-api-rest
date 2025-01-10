package com.api_restaurant.utils;

import com.api_restaurant.models.Client;
import com.api_restaurant.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FrequentClientHandlerTest {

    private FrequentClientHandler frequentClientHandler;
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        frequentClientHandler = new FrequentClientHandler(orderRepository);
    }

    @Test
    @DisplayName("Handle - Client Becomes Frequent")
    void handleClientBecomesFrequent() {
        Client client = new Client();
        client.setId(1L);

        when(orderRepository.countByClientId(1L)).thenReturn(10L);

        frequentClientHandler.handle(client);

        assertTrue(client.getFrequent());

        verify(orderRepository).countByClientId(1L);
    }

    @Test
    @DisplayName("Handle - Client Does Not Become Frequent")
    void handleClientDoesNotBecomeFrequent() {
        Client client = new Client();
        client.setId(1L);

        when(orderRepository.countByClientId(1L)).thenReturn(5L);

        frequentClientHandler.handle(client);

        assertFalse(client.getFrequent());

        verify(orderRepository).countByClientId(1L);
    }

    @Test
    @DisplayName("Set Next Handler")
    void setNext() {
        ClientHandler nextHandler = mock(ClientHandler.class);
        frequentClientHandler.setNext(nextHandler);

        Client client = new Client();
        frequentClientHandler.handle(client);

        verify(nextHandler).handle(client);
    }
}