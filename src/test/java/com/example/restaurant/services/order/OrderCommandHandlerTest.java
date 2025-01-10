package com.example.restaurant.services.order;

import com.example.restaurant.models.dto.order.OrderRequestDTO;
import com.example.restaurant.models.dto.order.OrderResponseDTO;
import com.example.restaurant.services.order.commands.CreateOrderCommand;
import com.example.restaurant.services.order.commands.DeleteOrderCommand;
import com.example.restaurant.services.order.commands.GetOrderByIdCommand;
import com.example.restaurant.services.order.commands.ListOrdersCommand;
import com.example.restaurant.services.order.commands.UpdateOrderCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class OrderCommandHandlerTest {

  @Mock
  private CreateOrderCommand createOrderCommand;

  @Mock
  private UpdateOrderCommand updateOrderCommand;

  @Mock
  private DeleteOrderCommand deleteOrderCommand;

  @Mock
  private GetOrderByIdCommand getOrderByIdCommand;

  @Mock
  private ListOrdersCommand listOrdersCommand;

  private OrderCommandHandler orderCommandHandler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    orderCommandHandler = new OrderCommandHandler(createOrderCommand, updateOrderCommand, deleteOrderCommand, getOrderByIdCommand, listOrdersCommand);
  }

  @Test
  @DisplayName("Crear orden")
  void createOrder() {
    OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
    OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

    when(createOrderCommand.execute(orderRequestDTO)).thenReturn(orderResponseDTO);

    OrderResponseDTO result = orderCommandHandler.createOrder(orderRequestDTO);

    assertNotNull(result);
    assertEquals(orderResponseDTO, result);

    verify(createOrderCommand, times(1)).execute(orderRequestDTO);
  }

  @Test
  @DisplayName("Actualizar orden")
  void updateOrder() {
    Long orderId = 1L;
    OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
    OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

    when(updateOrderCommand.execute(orderId, orderRequestDTO)).thenReturn(orderResponseDTO);

    OrderResponseDTO result = orderCommandHandler.updateOrder(orderId, orderRequestDTO);

    assertNotNull(result);
    assertEquals(orderResponseDTO, result);

    verify(updateOrderCommand, times(1)).execute(orderId, orderRequestDTO);
  }

  @Test
  @DisplayName("Eliminar orden")
  void deleteOrder() {
    Long orderId = 1L;

    doNothing().when(deleteOrderCommand).execute(orderId);

    orderCommandHandler.deleteOrder(orderId);

    verify(deleteOrderCommand, times(1)).execute(orderId);
  }

  @Test
  @DisplayName("Obtener orden por ID")
  void getOrderById() {
    Long orderId = 1L;
    OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

    when(getOrderByIdCommand.execute(orderId)).thenReturn(orderResponseDTO);

    OrderResponseDTO result = orderCommandHandler.getOrderById(orderId);

    assertNotNull(result);
    assertEquals(orderResponseDTO, result);

    verify(getOrderByIdCommand, times(1)).execute(orderId);
  }

  @Test
  @DisplayName("Listar todas las órdenes")
  void listOrders() {
    OrderResponseDTO orderResponseDTO1 = new OrderResponseDTO();
    OrderResponseDTO orderResponseDTO2 = new OrderResponseDTO();

    when(listOrdersCommand.execute()).thenReturn(List.of(orderResponseDTO1, orderResponseDTO2));

    List<OrderResponseDTO> result = orderCommandHandler.listOrders();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(orderResponseDTO1, result.get(0));
    assertEquals(orderResponseDTO2, result.get(1));

    verify(listOrdersCommand, times(1)).execute();
  }
}