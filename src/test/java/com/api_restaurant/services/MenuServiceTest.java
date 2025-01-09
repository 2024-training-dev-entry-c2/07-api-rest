package com.api_restaurant.services;

import com.api_restaurant.models.Menu;
import com.api_restaurant.repositories.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MenuServiceTest {

    private MenuService menuService;
    private MenuRepository menuRepository;

    @BeforeEach
    void setUp() {
        menuRepository = mock(MenuRepository.class);
        menuService = new MenuService(menuRepository);
    }

    @Test
    @DisplayName("Add Menu - Success")
    void addMenuSuccess() {
        Menu menu = new Menu("Menu 1");
        when(menuRepository.save(any(Menu.class))).thenReturn(menu);

        Menu result = menuService.addMenu(menu);

        assertEquals(menu.getName(), result.getName());

        Mockito.verify(menuRepository).save(any(Menu.class));
    }

    @Test
    @DisplayName("Add Menu - Failure")
    void addMenuFailure() {
        Menu menu = new Menu("Menu 1");
        when(menuRepository.save(any(Menu.class))).thenThrow(new RuntimeException("Failed to add menu"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuService.addMenu(menu);
        });

        assertEquals("Failed to add menu", exception.getMessage());

        Mockito.verify(menuRepository).save(any(Menu.class));
    }


    @Test
    @DisplayName("Get Menu - Found")
    void getMenuFound() {
        Menu menu = new Menu("Menu 1");
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        Optional<Menu> result = menuService.getMenu(1L);

        assertTrue(result.isPresent());
        assertEquals(menu.getName(), result.get().getName());

        Mockito.verify(menuRepository).findById(1L);
    }

    @Test
    @DisplayName("Get Menu - Not Found")
    void getMenuNotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Menu> result = menuService.getMenu(1L);

        assertTrue(result.isEmpty());

        Mockito.verify(menuRepository).findById(1L);
    }

    @Test
    @DisplayName("Get Menus - Found")
    void getMenusFound() {
        Menu menu1 = new Menu("Menu 1");
        Menu menu2 = new Menu("Menu 2");
        List<Menu> menus = List.of(menu1, menu2);

        when(menuRepository.findAll()).thenReturn(menus);

        List<Menu> result = menuService.getMenus();

        assertEquals(2, result.size());
        assertEquals(menu1.getName(), result.get(0).getName());
        assertEquals(menu2.getName(), result.get(1).getName());

        Mockito.verify(menuRepository).findAll();
    }

    @Test
    @DisplayName("Get Menus - Not Found")
    void getMenusNotFound() {
        when(menuRepository.findAll()).thenReturn(List.of());

        List<Menu> result = menuService.getMenus();

        assertTrue(result.isEmpty());

        Mockito.verify(menuRepository).findAll();
    }

    @Test
    @DisplayName("Update Menu - Success")
    void updateMenuSuccess() {
        Menu existingMenu = new Menu("Menu 1");
        when(menuRepository.findById(1L)).thenReturn(Optional.of(existingMenu));
        when(menuRepository.save(any(Menu.class))).thenReturn(existingMenu);

        Menu updatedMenu = new Menu("Menu Updated");
        Menu result = menuService.updateMenu(1L, updatedMenu);

        assertEquals(updatedMenu.getName(), result.getName());

        Mockito.verify(menuRepository).findById(1L);
        Mockito.verify(menuRepository).save(any(Menu.class));
    }

    @Test
    @DisplayName("Update Menu - Not Found")
    void updateMenuNotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        Menu updatedMenu = new Menu("Menu Updated");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuService.updateMenu(1L, updatedMenu);
        });

        assertEquals("Menu with id 1 could not be updated", exception.getMessage());

        Mockito.verify(menuRepository).findById(1L);
        Mockito.verify(menuRepository, never()).save(any(Menu.class));
    }

    @Test
    @DisplayName("Delete Menu - Success")
    void deleteMenuSuccess() {
        Menu menu = new Menu("Menu 1");
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        doNothing().when(menuRepository).deleteById(1L);

        menuService.deleteMenu(1L);

        Mockito.verify(menuRepository).findById(1L);
        Mockito.verify(menuRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Delete Menu - Not Found")
    void deleteMenuNotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuService.deleteMenu(1L);
        });

        assertEquals("Menu with id 1 not found", exception.getMessage());

        Mockito.verify(menuRepository).findById(1L);
        Mockito.verify(menuRepository, never()).deleteById(1L);
    }
}