package com.example.demo.services;

import com.example.demo.DTO.order.OrderRequestDTO;
import com.example.demo.DTO.order.OrderResponseDTO;
import com.example.demo.models.Client;
import com.example.demo.models.Dishfood;
import com.example.demo.models.Menu;
import com.example.demo.models.Order;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private DishfoodRepository dishfoodRepository;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderPricingService pricingService;
    @Mock
    private ClientValidationService clientValidationService;
    @Mock
    private DishfoodValidationService dishfoodValidationService;


    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testCreateOrder() {
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setClientId(1L);
        requestDTO.setLocalDate(LocalDate.now());
        requestDTO.setDishfoodIds(Arrays.asList(101L, 102L));


        Client mockClient = new Client();
        mockClient.setId(1L);
        mockClient.setName("John Doe");
        mockClient.setEmail("johndoe@example.com");


        Dishfood dish1 = new Dishfood();
        dish1.setId(101L);
        dish1.setName("Pizza");
        dish1.setPrice(12.99);

        Dishfood dish2 = new Dishfood();
        dish2.setId(102L);
        dish2.setName("Burger");
        dish2.setPrice(8.99);

        List<Dishfood> dishfoods = Arrays.asList(dish1, dish2);

        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setClient(mockClient);
        mockOrder.setLocalDate(requestDTO.getLocalDate());
        mockOrder.setDishfoods(dishfoods);
        mockOrder.setTotalPrice(21.98);


        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockClient));
        when(dishfoodRepository.findAllById(requestDTO.getDishfoodIds())).thenReturn(dishfoods);
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);


        OrderResponseDTO responseDTO = orderService.createOrder(requestDTO);


        assertEquals(1L, responseDTO.getId());
        assertEquals("John Doe", responseDTO.getClient().getName());
        assertEquals(2, responseDTO.getDishfoodIds().size());
        assertEquals(21.98, responseDTO.getTotalPrice());

        // Verificaciones de llamadas a métodos
        verify(clientRepository, times(1)).findById(1L);
        verify(dishfoodRepository, times(1)).findAllById(requestDTO.getDishfoodIds());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
    @Test
    void testGetOrderById() {
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setLocalDate(LocalDate.now());
        mockOrder.setTotalPrice(100.0);

        Client mockClient = new Client(1L, "John Doe", "john.doe@example.com", false, null);
        mockOrder.setClient(mockClient);
        mockOrder.setDishfoods(getDishfoodList());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));

        OrderResponseDTO response = orderService.getOrderById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John Doe", response.getClient().getName());
        assertEquals(100.0, response.getTotalPrice());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setLocalDate(LocalDate.now());
        order1.setTotalPrice(50.0);
        order1.setDishfoods(getDishfoodList());
        Order order2 = new Order();
        order2.setId(2L);
        order2.setLocalDate(LocalDate.now());
        order2.setTotalPrice(150.0);
        order2.setDishfoods(getDishfoodList());

        Client client1 = new Client(1L, "Alice", "alice@example.com", false, null);
        Client client2 = new Client(2L, "Bob", "bob@example.com", false, null);
        order1.setClient(client1);
        order2.setClient(client2);
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertEquals("Alice", orders.get(0).getClient().getName());
        assertEquals("Bob", orders.get(1).getClient().getName());
        verify(orderRepository, times(1)).findAll();
    }
    @Test
    void testUpdateOrder() {
        Client mockClient = new Client();
        mockClient.setId(1L);
        mockClient.setName("John Doe");
        mockClient.setEmail("john@example.com");

        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setLocalDate(LocalDate.now());
        mockOrder.setTotalPrice(100.0);
        mockOrder.setClient(mockClient);

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setClientId(1L);
        orderRequestDTO.setLocalDate(LocalDate.now().plusDays(1));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockClient));
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        OrderResponseDTO response = orderService.updateOrder(1L, orderRequestDTO);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(LocalDate.now().plusDays(1), response.getLocalDate());
        assertEquals(mockClient.getId(), response.getClient().getId());
        verify(orderRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(mockOrder);
    }
    @Test
    void testDeleteOrder() {
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setLocalDate(LocalDate.now());
        mockOrder.setTotalPrice(100.0);

        when(orderRepository.existsById(1L)).thenReturn(true);

        orderService.removeOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteOrderNotFound() {
        when(orderRepository.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> orderService.removeOrder(1L));
    }


    private List<Dishfood> getDishfoodList( ) {
        Menu menu =new  Menu(1L,"Menu prueba",List.of());
        return List.of(
                Dishfood.builder().id(1L).name("Pasta").price(12.5).isPopular(false).menu(menu).build(),
                Dishfood.builder().id(2L).name("pizza").price(17.5).isPopular(true).menu(menu).build(),
                Dishfood.builder().id(3L).name("hamburguesa").price(19.5).isPopular(false).menu(menu).build()

        );

    }
}