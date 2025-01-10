package com.example.demo.services;

import com.example.demo.models.Client;
import com.example.demo.models.Order;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientValidationServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private NotificationService notificationService;

    private ClientValidationService clientValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientValidationService = new ClientValidationService(clientRepository, orderRepository, notificationService);
    }

    @Test
    void checkClient_WhenClientBecomesFrequent_ShouldUpdateClientAndNotify() {
        Client client = Client.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .isOften(false)
                .build();
        Order order = new Order(1L, client, null, null);

        when(orderRepository.countByClient_Id(1L)).thenReturn(10L);

        clientValidationService.checkClient(order);

        verify(clientRepository, times(1)).save(client);
        verify(notificationService, times(1)).notifyObservers(order);
        assertTrue(client.getIsOften());
    }

    @Test
    void checkClient_WhenClientNotFrequentAndThresholdNotMet_ShouldNotUpdateOrNotify() {

        Client client = Client.builder()
                .id(2L)
                .name("Jane Smith")
                .email("jane.smith@example.com")
                .isOften(false)
                .build();
        Order order = new Order(1L, client, null, null);

        when(orderRepository.countByClient_Id(2L)).thenReturn(5L);

        clientValidationService.checkClient(order);

        verify(clientRepository, never()).save(any());
        verify(notificationService, never()).notifyObservers(order);
        assertFalse(client.getIsOften());
    }

    @Test
    void checkClient_WhenClientAlreadyFrequent_ShouldNotNotifyOrUpdate() {

        Client client = Client.builder()
                .id(3L)
                .name("Alice Johnson")
                .email("alice.johnson@example.com")
                .isOften(true)
                .build();
        Order order = new Order(1L, client, null, null);

        when(orderRepository.countByClient_Id(3L)).thenReturn(15L);

        clientValidationService.checkClient(order);

        verify(clientRepository, never()).save(any());
        verify(notificationService, never()).notifyObservers(order);
        assertTrue(client.getIsOften());
    }
}