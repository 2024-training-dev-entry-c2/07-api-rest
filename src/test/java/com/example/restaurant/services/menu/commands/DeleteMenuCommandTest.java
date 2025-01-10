package com.example.restaurant.services.menu.commands;

import com.example.restaurant.repositories.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteMenuCommandTest {

  @Mock
  private MenuRepository menuRepository;

  @InjectMocks
  private DeleteMenuCommand deleteMenuCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Eliminar menú existente")
  void execute_existingMenu() {
    Long menuId = 1L;

    when(menuRepository.existsById(menuId)).thenReturn(true);

    deleteMenuCommand.execute(menuId);

    verify(menuRepository, times(1)).deleteById(menuId);
  }

  @Test
  @DisplayName("Eliminar menú no existente")
  void execute_nonExistingMenu() {
    Long menuId = 1L;

    when(menuRepository.existsById(menuId)).thenReturn(false);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> deleteMenuCommand.execute(menuId));

    assertEquals("No se encontró menú con ID: " + menuId, exception.getMessage());
    verify(menuRepository, never()).deleteById(menuId);
  }
}