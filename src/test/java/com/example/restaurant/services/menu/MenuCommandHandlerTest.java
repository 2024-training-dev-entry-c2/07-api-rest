package com.example.restaurant.services.menu;

import com.example.restaurant.models.dto.menu.MenuRequestDTO;
import com.example.restaurant.models.dto.menu.MenuResponseDTO;
import com.example.restaurant.services.menu.commands.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuCommandHandlerTest {

  @Mock
  private CreateMenuCommand createMenuCommand;

  @Mock
  private UpdateMenuCommand updateMenuCommand;

  @Mock
  private DeleteMenuCommand deleteMenuCommand;

  @Mock
  private GetMenuByIdCommand getMenuByIdCommand;

  @Mock
  private ListMenusCommand listMenusCommand;

  @InjectMocks
  private MenuCommandHandler menuCommandHandler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Crear menú")
  void createMenu() {
    MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
    when(createMenuCommand.execute(menuRequestDTO)).thenReturn(menuResponseDTO);

    MenuResponseDTO result = menuCommandHandler.createMenu(menuRequestDTO);

    assertEquals(menuResponseDTO, result);
    verify(createMenuCommand, times(1)).execute(menuRequestDTO);
  }

  @Test
  @DisplayName("Actualizar menú")
  void updateMenu() {
    Long menuId = 1L;
    MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
    when(updateMenuCommand.execute(menuId, menuRequestDTO)).thenReturn(menuResponseDTO);

    MenuResponseDTO result = menuCommandHandler.updateMenu(menuId, menuRequestDTO);

    assertEquals(menuResponseDTO, result);
    verify(updateMenuCommand, times(1)).execute(menuId, menuRequestDTO);
  }

  @Test
  @DisplayName("Eliminar menú")
  void deleteMenu() {
    Long menuId = 1L;

    doNothing().when(deleteMenuCommand).execute(menuId);

    menuCommandHandler.deleteMenu(menuId);

    verify(deleteMenuCommand, times(1)).execute(menuId);
  }

  @Test
  @DisplayName("Obtener menú por ID")
  void getMenuById() {
    Long menuId = 1L;
    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
    when(getMenuByIdCommand.execute(menuId)).thenReturn(menuResponseDTO);

    MenuResponseDTO result = menuCommandHandler.getMenuById(menuId);

    assertEquals(menuResponseDTO, result);
    verify(getMenuByIdCommand, times(1)).execute(menuId);
  }

  @Test
  @DisplayName("Listar todos los menús")
  void listMenus() {
    List<MenuResponseDTO> menuResponseDTOList = List.of(new MenuResponseDTO());
    when(listMenusCommand.execute()).thenReturn(menuResponseDTOList);

    List<MenuResponseDTO> result = menuCommandHandler.listMenus();

    assertEquals(menuResponseDTOList, result);
    verify(listMenusCommand, times(1)).execute();
  }
}