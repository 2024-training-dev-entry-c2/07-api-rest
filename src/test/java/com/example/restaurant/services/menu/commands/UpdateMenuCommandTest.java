        package com.example.restaurant.services.menu.commands;

import com.example.restaurant.models.Menu;
import com.example.restaurant.models.dto.menu.MenuRequestDTO;
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

class UpdateMenuCommandTest {

  @Mock
  private MenuRepository menuRepository;

  @Mock
  private MenuMapper menuMapper;

  @InjectMocks
  private UpdateMenuCommand updateMenuCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Actualizar menú existente")
  void execute_existingMenu() {
    Long menuId = 1L;
    MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
    menuRequestDTO.setName("Updated Menu");
    menuRequestDTO.setDescription("Updated Description");
    Menu menu = new Menu(menuId, "Old Menu", "Old Description");
    Menu updatedMenu = new Menu(menuId, "Updated Menu", "Updated Description");
    MenuResponseDTO menuResponseDTO = new MenuResponseDTO(menuId, "Updated Menu", "Updated Description");

    when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
    when(menuRepository.save(menu)).thenReturn(updatedMenu);
    when(menuMapper.toDTO(updatedMenu)).thenReturn(menuResponseDTO);

    MenuResponseDTO result = updateMenuCommand.execute(menuId, menuRequestDTO);

    assertEquals(menuResponseDTO, result);
    verify(menuRepository, times(1)).findById(menuId);
    verify(menuRepository, times(1)).save(menu);
    verify(menuMapper, times(1)).toDTO(updatedMenu);
  }

  @Test
  @DisplayName("Actualizar menú no existente")
  void execute_nonExistingMenu() {
    Long menuId = 1L;
    MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
    menuRequestDTO.setName("Updated Menu");
    menuRequestDTO.setDescription("Updated Description");

    when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> updateMenuCommand.execute(menuId, menuRequestDTO));

    assertEquals("No se encontró menú con ID: " + menuId, exception.getMessage());
    verify(menuRepository, times(1)).findById(menuId);
    verify(menuRepository, never()).save(any(Menu.class));
    verify(menuMapper, never()).toDTO(any(Menu.class));
  }
}