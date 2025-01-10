package com.restaurant.restaurant.controllers;

import com.restaurant.restaurant.dtos.MenuDTO;
import com.restaurant.restaurant.dtos.DishDTO;
import com.restaurant.restaurant.enums.DishType;
import com.restaurant.restaurant.services.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuControllerTest {

  private final WebTestClient webTestClient;
  private final MenuService menuService;

  public MenuControllerTest(){
    menuService = mock(MenuService.class);
    webTestClient = WebTestClient.bindToController(new MenuController(menuService)).build();
  }

  @Test
  @DisplayName("Get all menus")
  void getAllMenus() {
    DishDTO dish1 = new DishDTO(1L, "Pasta", 12.99, DishType.COMUN);
    DishDTO dish2 = new DishDTO(2L, "Pizza", 15.49, DishType.POPULAR);
    MenuDTO menu1 = new MenuDTO(1L, "Menu 1", "Desc 1", List.of(dish1, dish2));
    MenuDTO menu2 = new MenuDTO(2L, "Menu 2", "Desc 2", List.of(dish2));

    when(menuService.findAll()).thenReturn(List.of(menu1, menu2));

    webTestClient
            .get()
            .uri("/api/menus")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data[0].name").isEqualTo(menu1.getName())
            .jsonPath("$.data[1].name").isEqualTo(menu2.getName());
  }

  @Test
  @DisplayName("Get menu by ID")
  void getMenuById() {
    DishDTO dish1 = new DishDTO(1L, "Pasta", 12.99, DishType.COMUN);
    MenuDTO menu = new MenuDTO(1L, "Menu 1", "Desc 1", List.of(dish1));

    when(menuService.findById(1L)).thenReturn(menu);

    webTestClient
            .get()
            .uri("/api/menus/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.id").isEqualTo(menu.getId().intValue())
            .jsonPath("$.data.name").isEqualTo(menu.getName());
  }

  @Test
  @DisplayName("Create menu")
  void createMenu() {
    DishDTO dish1 = new DishDTO(1L, "Pasta", 12.99, DishType.COMUN);
    MenuDTO menu = new MenuDTO(1L, "Menu 1", "Desc 1", List.of(dish1));

    when(menuService.createMenu(any(MenuDTO.class))).thenReturn(menu);

    webTestClient
            .post()
            .uri("/api/menus")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(menu)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.name").isEqualTo(menu.getName())
            .jsonPath("$.data.description").isEqualTo(menu.getDescription());
  }

  @Test
  @DisplayName("Update menu")
  void updateMenu() {
    DishDTO dish1 = new DishDTO(1L, "Pasta", 12.99, DishType.COMUN);
    MenuDTO menu = new MenuDTO(1L, "Menu 1", "Desc 1", List.of(dish1));
    MenuDTO updatedMenu = new MenuDTO(1L, "Updated Menu", "Updated Desc", List.of(dish1));

    when(menuService.updateMenu(eq(1L), any(MenuDTO.class))).thenReturn(updatedMenu);

    webTestClient
            .put()
            .uri("/api/menus/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(updatedMenu)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.id").isEqualTo(updatedMenu.getId().intValue())
            .jsonPath("$.data.name").isEqualTo(updatedMenu.getName());
  }

  @Test
  @DisplayName("Delete menu")
  void deleteMenu() {
    doNothing().when(menuService).deleteMenu(1L);

    webTestClient
            .delete()
            .uri("/api/menus/1")
            .exchange()
            .expectStatus().isNoContent();
  }
}
