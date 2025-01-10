package com.example.restaurant.services.menu.commands;

import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Menu;
import com.example.restaurant.models.dto.menu.MenuResponseDTO;
import com.example.restaurant.repositories.MenuRepository;
import com.example.restaurant.mappers.MenuMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListMenusCommandTest {
  private final MenuRepository menuRepository = mock(MenuRepository.class);
  private final MenuMapper menuMapper = mock(MenuMapper.class);

  @InjectMocks
  private ListMenusCommand listMenusCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Listar todos los menús")
  void execute() {

    Dish dish1 = new Dish(1L, "Dish 1", 10.0f);
    Dish dish2 = new Dish(2L, "Dish 2", 20.0f);

    MenuResponseDTO menu1ResponseDTO = new MenuResponseDTO();
    menu1ResponseDTO.setId(1L);
    menu1ResponseDTO.setName("Menu 1");
    menu1ResponseDTO.setDescription("Descripcion 1");

    MenuResponseDTO menu2ResponseDTO = new MenuResponseDTO();
    menu2ResponseDTO.setId(2L);
    menu2ResponseDTO.setName("Menu 2");
    menu2ResponseDTO.setDescription("Descripcion 2");

    when(menuRepository.findAll()).thenReturn(getMenuList());
    when(menuMapper.toDTO(getMenuList().get(0))).thenReturn(menu1ResponseDTO);
    when(menuMapper.toDTO(getMenuList().get(1))).thenReturn(menu2ResponseDTO);

    List<MenuResponseDTO> result = listMenusCommand.execute();

    assertEquals(2, result.size());
    assertEquals(1L, result.get(0).getId());
    assertEquals("Menu 1", result.get(0).getName());
    assertEquals("Descripcion 1", result.get(0).getDescription());
    assertEquals(2L, result.get(1).getId());
    assertEquals("Menu 2", result.get(1).getName());
    assertEquals("Descripcion 2", result.get(1).getDescription());
  }

  public List<Menu> getMenuList() {
    Dish dish1 = new Dish(1L, "Dish 1", 10.0f);
    Dish dish2 = new Dish(2L, "Dish 2", 20.0f);

    Menu menu1 = new Menu(1L, "Menu 1", "Descripcion 1", List.of(dish2));
    Menu menu2 = new Menu(2L, "Menu 2", "Descripcion 2", List.of(dish1));

    return List.of(menu1, menu2);
  }
}