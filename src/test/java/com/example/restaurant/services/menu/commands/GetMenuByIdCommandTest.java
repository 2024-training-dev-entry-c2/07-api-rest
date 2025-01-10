package com.example.restaurant.services.menu.commands;

import com.example.restaurant.models.Menu;
import com.example.restaurant.models.dto.menu.MenuResponseDTO;
import com.example.restaurant.repositories.MenuRepository;
import com.example.restaurant.mappers.MenuMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetMenuByIdCommandTest {

  @Mock
  private MenuRepository menuRepository;

  @Mock
  private MenuMapper menuMapper;

  @InjectMocks
  private GetMenuByIdCommand getMenuByIdCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Obtener menú existente por ID")
  void execute_existingMenu() {
    Long menuId = 1L;
    Menu menu = new Menu();
    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();

    when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
    when(menuMapper.toDTO(menu)).thenReturn(menuResponseDTO);

    MenuResponseDTO result = getMenuByIdCommand.execute(menuId);

    assertEquals(menuResponseDTO, result);
    verify(menuRepository, times(1)).findById(menuId);
    verify(menuMapper, times(1)).toDTO(menu);
  }

  @Test
  @DisplayName("Obtener menú no existente por ID")
  void execute_nonExistingMenu() {
    Long menuId = 1L;

    when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> getMenuByIdCommand.execute(menuId));

    assertEquals("No se encontró menú con ID: " + menuId, exception.getMessage());
    verify(menuRepository, times(1)).findById(menuId);
    verify(menuMapper, never()).toDTO(any());
  }
}