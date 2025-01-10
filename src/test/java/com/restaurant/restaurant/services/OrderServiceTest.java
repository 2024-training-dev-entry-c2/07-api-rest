package com.restaurant.restaurant.services;

import com.restaurant.restaurant.dtos.CreateOrderDTO;
import com.restaurant.restaurant.dtos.OrderDTO;
import com.restaurant.restaurant.exceptions.BusinessException;
import com.restaurant.restaurant.exceptions.ResourceNotFoundException;
import com.restaurant.restaurant.models.ClientModel;
import com.restaurant.restaurant.models.DishModel;
import com.restaurant.restaurant.models.OrderModel;
import com.restaurant.restaurant.repositories.ClientRepository;
import com.restaurant.restaurant.repositories.DishRepository;
import com.restaurant.restaurant.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private ClientRepository clientRepository;

  @Mock
  private DishRepository dishRepository;

  @InjectMocks
  private OrderService orderService;

  private ClientModel clientModel;
  private DishModel dishModel;
  private OrderModel orderModel;
  private OrderDTO orderDTO;
  private CreateOrderDTO createOrderDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    clientModel = new ClientModel();
    clientModel.setId(1L);
    clientModel.setType(com.restaurant.restaurant.enums.ClientType.COMUN);

    dishModel = new DishModel();
    dishModel.setId(1L);
    dishModel.setName("Pasta");
    dishModel.setPrice(10.0);
    dishModel.setType(com.restaurant.restaurant.enums.DishType.COMUN);

    orderModel = new OrderModel();
    orderModel.setId(1L);
    orderModel.setClient(clientModel);
    orderModel.setDishes(List.of(dishModel));
    orderModel.setDate(LocalDateTime.now());
    orderModel.setTotalCost(10.0);

    orderDTO = new OrderDTO(1L, 1L, List.of(1L), LocalDateTime.now(), 10.0);

    createOrderDTO = new CreateOrderDTO();
    createOrderDTO.setClientId(1L);
    createOrderDTO.setDishIds(List.of(1L));
  }

  @Test
  @DisplayName("Test findAll() - Should return all orders")
  void findAll() {
    when(orderRepository.findAll()).thenReturn(List.of(orderModel));

    List<OrderDTO> orders = orderService.findAll();

    assertNotNull(orders);
    assertEquals(1, orders.size());
    assertEquals(orderDTO.getClientId(), orders.get(0).getClientId());
  }

  @Test
  @DisplayName("Test findById() - Should return order by id")
  void findById() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orderModel));

    OrderDTO foundOrder = orderService.findById(1L);

    assertNotNull(foundOrder);
    assertEquals(orderDTO.getClientId(), foundOrder.getClientId());
  }

  @Test
  @DisplayName("Test findById() - Should throw ResourceNotFoundException if order does not exist")
  void findByIdNotFound() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      orderService.findById(999L);
    });

    assertEquals("Order not found with id 999", exception.getMessage());
  }

  @Test
  @DisplayName("Test findByClientId() - Should return orders by client id")
  void findByClientId() {
    when(clientRepository.existsById(anyLong())).thenReturn(true);
    when(orderRepository.findByClientId(anyLong())).thenReturn(List.of(orderModel));

    List<OrderDTO> orders = orderService.findByClientId(1L);

    assertNotNull(orders);
    assertEquals(1, orders.size());
    assertEquals(orderDTO.getClientId(), orders.get(0).getClientId());
  }

  @Test
  @DisplayName("Test findByClientId() - Should throw ResourceNotFoundException if client does not exist")
  void findByClientIdClientNotFound() {
    when(clientRepository.existsById(anyLong())).thenReturn(false);

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      orderService.findByClientId(999L);
    });

    assertEquals("Client not found with id 999", exception.getMessage());
  }

  @Test
  @DisplayName("Test createOrder() - Should create and return a new order")
  void createOrder() {
    when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientModel));
    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dishModel));
    when(orderRepository.save(any(OrderModel.class))).thenReturn(orderModel);

    OrderDTO createdOrder = orderService.createOrder(createOrderDTO);

    assertNotNull(createdOrder);
    assertEquals(orderDTO.getClientId(), createdOrder.getClientId());
    assertEquals(orderDTO.getTotalCost(), createdOrder.getTotalCost());
  }

  @Test
  @DisplayName("Test createOrder() - Should throw ResourceNotFoundException if client is not found")
  void createOrderClientNotFound() {
    when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      orderService.createOrder(createOrderDTO);
    });

    assertEquals("Client not found", exception.getMessage());
  }

  @Test
  @DisplayName("Test createOrder() - Should throw ResourceNotFoundException if dish is not found")
  void createOrderDishNotFound() {
    when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientModel));
    when(dishRepository.findById(anyLong())).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      orderService.createOrder(createOrderDTO);
    });

    assertEquals("Dish not found 1", exception.getMessage());
  }

  @Test
  @DisplayName("Test createOrder() - Should throw BusinessException if dish list is empty")
  void createOrderEmptyDishList() {
    createOrderDTO.setDishIds(List.of());
    when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientModel));

    BusinessException exception = assertThrows(BusinessException.class, () -> {
      orderService.createOrder(createOrderDTO);
    });

    assertEquals("Dish list cannot be empty", exception.getMessage());
  }

  @Test
  @DisplayName("Test createOrder() - Should successfully create an order and update the state of the client and dish")
  void createOrderSuccess() {
    when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientModel));
    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dishModel));
    when(orderRepository.save(any(OrderModel.class))).thenReturn(orderModel);
    when(orderRepository.countOrdersByClientId(anyLong())).thenReturn(10); // Cliente con más de 10 pedidos
    when(orderRepository.countOrdersByDishId(anyLong())).thenReturn(100); // Plato con más de 100 ventas

    OrderDTO createdOrder = orderService.createOrder(createOrderDTO);

    assertNotNull(createdOrder);
    assertEquals(orderDTO.getClientId(), createdOrder.getClientId());
    assertEquals(orderDTO.getTotalCost(), createdOrder.getTotalCost());

    // Verificamos que se haya actualizado el tipo de cliente
    assertEquals(com.restaurant.restaurant.enums.ClientType.FRECUENT, clientModel.getType());
    verify(clientRepository, times(1)).save(clientModel);

    // Verificamos que el plato haya sido actualizado a "POPULAR"
    assertEquals(com.restaurant.restaurant.enums.DishType.POPULAR, dishModel.getType());
    verify(dishRepository, times(1)).save(dishModel);
  }

  @Test
  @DisplayName("Test updateOrder() - Should update and return the order")
  void updateOrder() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orderModel));
    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dishModel));
    when(orderRepository.save(any(OrderModel.class))).thenReturn(orderModel);

    OrderDTO updatedOrderDTO = new OrderDTO(1L, 1L, List.of(1L), LocalDateTime.now(), 10.0);
    OrderDTO updatedOrder = orderService.updateOrder(1L, updatedOrderDTO);

    assertNotNull(updatedOrder);
    assertEquals(updatedOrderDTO.getClientId(), updatedOrder.getClientId());
  }

  @Test
  @DisplayName("Test updateOrder() - Should throw ResourceNotFoundException if order does not exist")
  void updateOrderNotFound() {
    when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      orderService.updateOrder(999L, orderDTO);
    });

    assertEquals("Order not found with id 999", exception.getMessage());
  }

  @Test
  @DisplayName("Test deleteOrder() - Should delete an order")
  void deleteOrder() {
    when(orderRepository.existsById(anyLong())).thenReturn(true);
    doNothing().when(orderRepository).deleteById(anyLong());

    orderService.deleteOrder(1L);

    verify(orderRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Test deleteOrder() - Should throw ResourceNotFoundException if order does not exist")
  void deleteOrderNotFound() {
    when(orderRepository.existsById(anyLong())).thenReturn(false);

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      orderService.deleteOrder(999L);
    });

    assertEquals("Order not found with id 999", exception.getMessage());
  }
}
