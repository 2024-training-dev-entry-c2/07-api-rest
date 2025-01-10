package com.example.restaurant.services.order.commands;

import com.example.restaurant.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteOrderCommandTest {

  @Mock
  private OrderRepository orderRepository;

  private DeleteOrderCommand deleteOrderCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    deleteOrderCommand = new DeleteOrderCommand(orderRepository);
  }

  @Test
  @DisplayName("Eliminar orden existente")
  void execute_existingOrder() {
    Long orderId = 1L;

    when(orderRepository.existsById(orderId)).thenReturn(true);

    deleteOrderCommand.execute(orderId);

    verify(orderRepository, times(1)).deleteById(orderId);
  }

  @Test
  @DisplayName("Lanzar excepción al eliminar orden no existente")
  void execute_nonExistingOrder() {
    Long orderId = 1L;

    when(orderRepository.existsById(orderId)).thenReturn(false);

    assertThrows(IllegalArgumentException.class, () -> deleteOrderCommand.execute(orderId));

    verify(orderRepository, never()).deleteById(orderId);
  }
}