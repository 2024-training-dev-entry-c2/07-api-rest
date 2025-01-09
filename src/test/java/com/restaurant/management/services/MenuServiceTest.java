package com.restaurant.management.services;

import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Menu;
import com.restaurant.management.repositories.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuServiceTest {
  private final MenuRepository menuRepository;
  private final MenuService menuService;

  MenuServiceTest() {
    this.menuRepository = mock(MenuRepository.class);
    this.menuService = new MenuService(menuRepository);
  }

  @Test
  @DisplayName("Agregar menú")
  void addMenu() {
    Dish dish1 = mock(Dish.class);
    Menu menu = new Menu(1L, "name");
    menu.setDishes(Arrays.asList(dish1));
    when(menuRepository.save(eq(menu))).thenReturn(menu);

    Menu actualMenu = menuService.addMenu(menu);

    assertEquals(menu.getId(), actualMenu.getId());
    assertEquals(menu.getName(), actualMenu.getName());

    verify(dish1).setMenu(menu);
    verify(menuRepository).save(menu);
  }

  @Test
  @DisplayName("Agregar plato a menú")
  void addDishToMenu() {
    Menu menu = new Menu(1L, "name");
    Dish dish = new Dish(1L, "name", "description", 10f, null);
    when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menu));

    Dish addedDish = menuService.addDishToMenu(1L, dish);

    assertTrue(menu.getDishes().contains(dish));
    assertEquals(menu, dish.getMenu());

    verify(menuRepository).findById(anyLong());
  }

  @Test
  @DisplayName("Obtener menú por id")
  void getMenuById() {
    Menu menu = new Menu(1L, "name");
    when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menu));

    Optional<Menu> retrievedMenu = menuService.getMenuById(1L);

    assertTrue(retrievedMenu.isPresent());
    assertEquals(menu.getId(), retrievedMenu.get().getId());

    verify(menuRepository).findById(anyLong());
  }

  @Test
  @DisplayName("Obtener lista de menús")
  void getMenus() {
    when(menuRepository.findAll()).thenReturn(getMenuList());

    List<Menu> retrievedMenus = menuService.getMenus();

    assertEquals(getMenuList().size(), retrievedMenus.size());
    assertEquals(getMenuList().get(0).getId(), retrievedMenus.get(0).getId());
    assertEquals(getMenuList().get(1).getId(), retrievedMenus.get(1).getId());
    assertEquals(getMenuList().get(2).getId(), retrievedMenus.get(2).getId());

    verify(menuRepository).findAll();

  }

  @Test
  @DisplayName("Actualizar menú exitoso")
  void updateMenu() {
    Menu menu = new Menu(1L, "name");
    Dish dish1 = mock(Dish.class);
    Menu updatedMenu = new Menu("Updated Name");
    updatedMenu.setDishes(Arrays.asList(dish1));
    when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menu));
    when(menuRepository.save(eq(menu))).thenAnswer(invocation -> invocation.getArgument(0));

    Menu result = menuService.updateMenu(1L, updatedMenu);

    assertEquals("Updated Name", result.getName());

    verify(dish1, times(2)).setMenu(result);
    verify(menuRepository).findById(anyLong());
    verify(menuRepository).save(menu);
  }

  @Test
  @DisplayName("Actualizar menú no encontrado lanza excepción")
  void updateMenuNotFound() {
    when(menuRepository.findById(anyLong())).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> menuService.updateMenu(1L, new Menu()));

    assertEquals("Menú con id 1 no se pudo actualizar.", exception.getMessage());

    verify(menuRepository).findById(anyLong());
    verify(menuRepository, never()).save(any());
  }

  @Test
  @DisplayName("Eliminar menú exitoso")
  void deleteMenu() {
    Dish dish1 = mock(Dish.class);
    Menu menu = new Menu(1L, "name");
    menu.setDishes(Arrays.asList(dish1));
    when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menu));
    doNothing().when(menuRepository).deleteById(anyLong());

    menuService.deleteMenu(1L);

    verify(dish1).setMenu(null);
    verify(menuRepository).deleteById(anyLong());
  }

  @Test
  @DisplayName("Eliminar menú no encontrado")
  void deleteMenuNotFound() {
    when(menuRepository.findById(anyLong())).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> menuService.deleteMenu(1L));

    assertEquals("Menú no encontrado", exception.getMessage());

    verify(menuRepository).findById(anyLong());
    verify(menuRepository, never()).deleteById(anyLong());
  }

  private List<Menu> getMenuList() {
    return List.of(new Menu(1L, "A"),
      new Menu(2L, "B"),
      new Menu(3L, "C"));
  }
}