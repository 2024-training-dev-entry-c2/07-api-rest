package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.models.Menu;
import com.restaurant.restaurant_management.repositories.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuServiceTest {
  private MenuRepository menuRepository;
  private MenuService menuService;

  private Menu newMenu;
  private Menu menu;
  private Menu menu2;
  private List<Menu> menus;
  private Menu updatedMenu;
  private Menu savedMenu;

  @BeforeEach
  void setUp() {
    menuRepository = mock(MenuRepository.class);
    menuService = new MenuService(menuRepository);

    newMenu = new Menu(null, "Menu 1", "Descripcion del menu 1", true);
    menu = new Menu(1, "Menu 1", "Descripcion del menu 1", true);
    menu2 = new Menu(2, "Menu 2", "Descripcion del menu 2", true);
    menus = List.of(menu, menu2);
    updatedMenu = new Menu(null, "Menu actualizado", "Nueva descripcion del menu 1", true);
    savedMenu = new Menu(1, "Menu actualizado", "Nueva descripcion del menu 1", true);

  }

  @Test
  @DisplayName("Save menu")
  void saveMenu() {
    when(menuRepository.save(newMenu)).thenReturn(menu);
    Menu result = menuService.saveMenu(newMenu);
    assertNotNull(result);
    assertEquals(menu.getId(), result.getId());
    Mockito.verify(menuRepository, Mockito.times(1)).save(newMenu);
  }

  @Test
  @DisplayName("Get menu")
  void getMenu() {
    when(menuRepository.findById(1)).thenReturn(Optional.of(menu));
    Optional<Menu> result = menuService.getMenu(1);
    assertTrue(result.isPresent());
    assertEquals(menu.getId(), result.get().getId());
    Mockito.verify(menuRepository, Mockito.times(1)).findById(1);
  }

  @Test
  @DisplayName("List menus")
  void listMenus() {
    when(menuRepository.findAll()).thenReturn(menus);
    List<Menu> result = menuService.listMenus();
    assertNotNull(result);
    assertEquals(menus.size(), result.size());
    Mockito.verify(menuRepository, Mockito.times(1)).findAll();
  }

  @Test
  @DisplayName("List active menus")
  void listActiveMenus() {
    when(menuRepository.findByActiveTrue()).thenReturn(menus);
    List<Menu> result = menuService.listActiveMenus();
    assertNotNull(result);
    assertEquals(menus.size(), result.size());
    Mockito.verify(menuRepository, Mockito.times(1)).findByActiveTrue();
  }

  @Test
  @DisplayName("Update menu")
  void updateMenu() {
    when(menuRepository.findById(1)).thenReturn(Optional.of(menu));
    when(menuRepository.save(any(Menu.class))).thenReturn(savedMenu);

    Menu result = menuService.updateMenu(1, updatedMenu);

    assertEquals(savedMenu.getId(), result.getId());
    assertEquals("Menu actualizado", result.getMenuName());
    assertEquals("Nueva descripcion del menu 1", result.getDescription());

    Mockito.verify(menuRepository, Mockito.times(1)).findById(1);
    Mockito.verify(menuRepository, Mockito.times(1)).save(any(Menu.class));
  }

  @Test
  @DisplayName("Update menu - Menu not found")
  void updateMenu_NotFound() {
    when(menuRepository.findById(1)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> menuService.updateMenu(1, new Menu()));
    assertEquals("Menu con el id 1 no pudo ser actualizado", exception.getMessage());
    Mockito.verify(menuRepository, Mockito.times(1)).findById(1);
    Mockito.verify(menuRepository, Mockito.never()).save(any(Menu.class));
  }

}