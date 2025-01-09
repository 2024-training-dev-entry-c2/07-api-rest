package com.example.demo.services;

import com.example.demo.DTO.converterDTO.OrderConverter;
import com.example.demo.DTO.order.OrderRequestDTO;
import com.example.demo.DTO.order.OrderResponseDTO;
import com.example.demo.models.Client;
import com.example.demo.models.Dishfood;
import com.example.demo.models.Menu;
import com.example.demo.models.Order;
import com.example.demo.observer.FrequentClientObserver;
import com.example.demo.observer.PopularDishObserver;
import com.example.demo.observer.Subject;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.DishfoodRepository;
import com.example.demo.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private DishfoodRepository dishfoodRepository;

    @Mock
    private Subject subject;

    @InjectMocks
    private OrderService orderService;

    private OrderRequestDTO orderRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderRequestDTO = new OrderRequestDTO(1L, LocalDate.now(), Arrays.asList(1L, 2L));
    }


    @Test
    void createOrder() {
        Client client = Client.builder()
                .id(1L)
                .name("Mario")
                .email("Mario@bros.com")
                .isOften(false)
                .build();
        Menu menu = new Menu();
        menu.setName("menu prueba");
        Order order = Order.builder()
                .id(1L)
                .client(client)
                .localDate(orderRequestDTO.getLocalDate())
                .dishfoods(getDishfoodList(menu))
                .totalPrice(15.0)
                .build();
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        when(dishfoodRepository.findAllById(orderRequestDTO.getDishfoodIds()))
                .thenReturn(getDishfoodList(menu));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        OrderResponseDTO response = orderService.createOrder(orderRequestDTO);
        assertNotNull(response);
        assertEquals(15.0, response.getTotalPrice());
        assertEquals("Mario", response.getClient().getName());
        assertEquals(Arrays.asList(1L, 2L, 3L), response.getDishfoodIds());

        verify(clientRepository).findById(1L);
        verify(dishfoodRepository).findAllById(orderRequestDTO.getDishfoodIds());
        verify(orderRepository).save(any(Order.class));

//        verify(subject).notifyObservers(anyString());
    }

    @Test
    void createOrder_ClientNotFound() {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1L, LocalDate.now(), Collections.singletonList(1L));

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(orderRequestDTO);
        });

        assertEquals("Client not found", exception.getMessage());

        verify(clientRepository, times(1)).findById(1L);
        verify(dishfoodRepository, never()).findAllById(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_DishfoodsNotFound() {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1L, LocalDate.now(), Collections.singletonList(1L));

        Client client = new Client();
        client.setId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(dishfoodRepository.findAllById(orderRequestDTO.getDishfoodIds())).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(orderRequestDTO);
        });

        assertEquals("Dishfoods not found", exception.getMessage());
        verify(clientRepository).findById(1L);
        verify(dishfoodRepository).findAllById(orderRequestDTO.getDishfoodIds());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void getOrderById() {
        Client client = Client.builder()
                .id(1L)
                .name("Mario")
                .email("Mario@bros.com")
                .isOften(false)
                .build();
        Order order = OrderConverter.toEntity(orderRequestDTO, client, List.of(), 12.00);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderResponseDTO responseDTO = orderService.getOrderById(1L);
        assertNotNull(responseDTO);
        assertEquals("Mario", responseDTO.getClient().getName());
        assertEquals(LocalDate.now(), responseDTO.getLocalDate());

    }

    @Test
    void getAllOrders() {
        when(orderRepository.findAll()).thenReturn(getOrderList());
        List<OrderResponseDTO> requestDTOS = orderService.getAllOrders();
        assertNotNull(requestDTOS);
        assertEquals("jerardo", requestDTOS.get(0).getClient().getName());
        assertEquals(LocalDate.now(), requestDTOS.get(1).getLocalDate());

    }

    @Test
    void updateOrder() {
        Long orderId = 1L;
        Long clientId = 2L;
        List<Long> dishfoodIds = List.of(3L, 4L);

        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .clientId(clientId)
                .localDate(LocalDate.now())
                .dishfoodIds(dishfoodIds)
                .build();

        Client mockClient = new Client();
        mockClient.setId(clientId);
        mockClient.setName("Mock Client");

        Dishfood mockDish1 = new Dishfood();
        mockDish1.setId(3L);
        mockDish1.setPrice(10.0);

        Dishfood mockDish2 = new Dishfood();
        mockDish2.setId(4L);
        mockDish2.setPrice(15.0);

        Order existingOrder = Order.builder()
                .id(orderId)
                .client(mockClient)
                .dishfoods(List.of(mockDish1))
                .localDate(LocalDate.now().minusDays(1))
                .totalPrice(10.0)
                .build();

        Order updatedOrder = Order.builder()
                .id(orderId)
                .client(mockClient)
                .dishfoods(List.of(mockDish1, mockDish2))
                .localDate(orderRequestDTO.getLocalDate())
                .totalPrice(25.0)
                .build();

        // Configurar los mocks
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(clientRepository.getReferenceById(clientId)).thenReturn(mockClient);
        when(dishfoodRepository.findAllById(dishfoodIds)).thenReturn(List.of(mockDish1, mockDish2));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        // Llamar al método que se quiere probar
        OrderResponseDTO response = orderService.updateOrder(orderId, orderRequestDTO);

        // Verificaciones
        assertNotNull(response);
        assertEquals(orderId, response.getId());
        assertEquals(clientId, response.getClient().getId());
        assertEquals(2, response.getDishfoodIds().size());
        assertEquals(25.0, response.getTotalPrice());

        // Verificar interacciones
        verify(orderRepository).findById(orderId);
        verify(clientRepository).getReferenceById(clientId);
        verify(dishfoodRepository).findAllById(dishfoodIds);
        verify(orderRepository).save(any(Order.class));
    }


    @Test
    void removeOrder() {
        Long menuId=1L;
        when(orderRepository.existsById(menuId)).thenReturn(true);
        orderService.removeOrder(menuId);
        verify(orderRepository).existsById(menuId);
        verify(orderRepository).deleteById(menuId);
    }

    @Test
    void checkClienFrecuent(){

        Client client = new Client();
        client.setId(1L);
        client.setIsOften(false);
        client.setName("Mario");
        when(orderRepository.countByClient_Id(client.getId())).thenReturn(10L); // Simula que tiene 10 pedidos
        when(clientRepository.save(client)).thenReturn(client);

        Subject subject = new Subject();
        FrequentClientObserver frequentObserver = mock(FrequentClientObserver.class);
        PopularDishObserver popularObserver = mock(PopularDishObserver.class);
        this.subject.addObserver(frequentObserver);
        this.subject.addObserver(popularObserver);
        OrderService orderService = new OrderService(
                orderRepository,
                clientRepository,
                dishfoodRepository,
                frequentObserver,
                popularObserver
        );
        orderService.checkClient(client);
        assertTrue(client.getIsOften());
        verify(clientRepository, times(1)).save(client);
        verify(frequentObserver, times(1)).update("El cliente Mario ahora es un cliente frecuente.");
    }
    @Test
    void checkClienFrecuentwithDish(){
        Menu menu = new Menu("menu");
        Client client = new Client();
        client.setId(1L);
        client.setIsOften(false);
        client.setName("Mario");

        List<Dishfood> dishfoodList = getDishfoodList(menu);


        when(dishfoodRepository.findAllById(orderRequestDTO.getDishfoodIds())).thenReturn(dishfoodList);
        when(orderRepository.countByClient_Id(client.getId())).thenReturn(11L);
        when(clientRepository.save(client)).thenReturn(client);
        when(orderRepository.countByDishfoods_Id(dishfoodList.get(0).getId())).thenReturn(13L);


        FrequentClientObserver frequentObserver = mock(FrequentClientObserver.class);
        PopularDishObserver popularObserver = mock(PopularDishObserver.class);


        OrderService orderService = new OrderService(
                orderRepository,
                clientRepository,
                dishfoodRepository,
                frequentObserver,
                popularObserver
        );


        orderService.checkClient(client);
        orderService.checkDishFood(dishfoodList);


        assertTrue(client.getIsOften(), "El cliente debería ser marcado como frecuente");
        assertTrue(dishfoodList.get(0).getIsPopular(), "El plato debería ser marcado como popular");

        verify(clientRepository, times(1)).save(client);
        verify(dishfoodRepository, times(1)).save(dishfoodList.get(0));
        verify(frequentObserver, times(1)).update("El cliente Mario ahora es un cliente frecuente.");
        verify(popularObserver, times(1)).update("El plato Pasta ahora es popular.");

    }
    @Test
    void removeOrder_OrderNotFound_ThrowsException() {
        Long nonExistentId = 1L;
        when(orderRepository.existsById(nonExistentId)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> orderService.removeOrder(nonExistentId));
        assertEquals("Order not found", exception.getMessage());

        verify(orderRepository, never()).deleteById(nonExistentId);
    }

    private List<Dishfood> getDishfoodList(Menu menu) {
        return List.of(
                Dishfood.builder().id(1L).name("Pasta").price(12.5).isPopular(false).menu(menu).build(),
                Dishfood.builder().id(2L).name("pizza").price(17.5).isPopular(true).menu(menu).build(),
                Dishfood.builder().id(3L).name("hamburguesa").price(19.5).isPopular(false).menu(menu).build()

        );

    }

    private List<Order> getOrderList() {
        return List.of(
                Order.builder()
                        .id(1L)
                        .client(Client.builder()
                                .id(1L)
                                .email("m@gmail.com")
                                .name("jerardo")
                                .isOften(false)
                                .build()
                        )
                        .localDate(LocalDate.now())
                        .dishfoods(List.of(
                                        Dishfood.builder()
                                                .id(1L)
                                                .name("enchiladas")
                                                .price(20.0)
                                                .isPopular(false)
                                                .build()
                                )
                        ).build(),
                Order.builder()
                        .id(2L)
                        .client(Client.builder()
                                .id(2L)
                                .email("las@gmail.com")
                                .name("vacunas")
                                .isOften(false)
                                .build()
                        )
                        .localDate(LocalDate.now())
                        .dishfoods(List.of(
                                Dishfood.builder()
                                        .id(2L)
                                        .name("tacos")
                                        .price(23.0)
                                        .isPopular(false)
                                        .build()
                        )).build(),
                Order.builder()
                        .id(3L)
                        .client(Client.builder()
                                .id(3L)
                                .email("desde@gmail.com")
                                .name("5meses")
                                .isOften(false)
                                .build()
                        )
                        .localDate(LocalDate.now())
                        .dishfoods(List.of(
                                Dishfood.builder()
                                        .id(2L)
                                        .name("tacos")
                                        .price(23.0)
                                        .isPopular(false)
                                        .build())).build()

        );

    }


}