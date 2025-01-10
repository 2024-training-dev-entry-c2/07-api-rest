package com.example.demo.services;

import com.example.demo.models.Client;
import com.example.demo.models.Dishfood;
import com.example.demo.models.Menu;
import com.example.demo.models.Order;
import com.example.demo.observer.FrequentClientObserver;
import com.example.demo.observer.PopularDishObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class NotificationServiceTest {
    @Mock
    private FrequentClientObserver frequentClientObserver;

    @Mock
    private PopularDishObserver popularDishObserver;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        notificationService = new NotificationService(frequentClientObserver, popularDishObserver);
    }

    @Test
    void notifyObservers_WhenClientIsFrequent_ShouldNotifyFrequentClientObserver() {
        Client client = new Client(1L,"Mario bros","martio@bros.com", true,List.of());
        Order order = new Order(1L,client, LocalDate.now(),getDishfoodList());

        notificationService.notifyObservers(order);

        verify(frequentClientObserver, times(1)).update("El cliente Mario bros ahora es un cliente frecuente.");
    }

    @Test
    void notifyObserversdish_WhenDishIsPopular_ShouldNotifyPopularDishObserver() {

        Client client = new Client(1L,"Mario bros","martio@bros.com", true,List.of());
        Order order = new Order(1L,client, LocalDate.now(),getDishfoodList());

        notificationService.notifyObserversdish(order);

        verify(popularDishObserver, times(1)).update("El plato Pizza ahora es popular.");
    }

    @Test
    void notifyObserversdish_WhenNoPopularDish_ShouldNotNotifyPopularDishObserver() {
        Client client = new Client(1L,"Mario bros","martio@bros.com", true,List.of());
        Dishfood regularDish = new Dishfood(1L,"Burger",20.0, false, new Menu(),List.of());
        Order order = new Order(1L,client, LocalDate.now(),List.of(regularDish));

        notificationService.notifyObserversdish(order);

        verify(popularDishObserver, never()).update(anyString());
    }

    @Test
    void notifyObservers_WhenClientIsNotFrequent_ShouldNotNotifyFrequentClientObserver() {
        Client client = new Client(1L,"Mario bros","martio@bros.com", false,List.of());
        Order order = new Order(1L,client,LocalDate.now(),getDishfoodList());

        notificationService.notifyObservers(order);

        verify(frequentClientObserver, never()).update(anyString());
    }


    private List<Dishfood> getDishfoodList() {
        Menu menu = new Menu(1L, "Menu prueba", List.of());
        return List.of(
                Dishfood.builder().id(1L).name("Pasta").price(12.5).isPopular(false).menu(menu).build(),
                Dishfood.builder().id(2L).name("Pizza").price(17.5).isPopular(true).menu(menu).build(),
                Dishfood.builder().id(3L).name("hamburguesa").price(19.5).isPopular(false).menu(menu).build()

        );

    }

}

