package com.restaurant.management.controllers;

import com.restaurant.management.models.Menu;
import com.restaurant.management.models.dto.MenuRequestDTO;
import com.restaurant.management.models.dto.MenuResponseDTO;
import com.restaurant.management.services.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuControllerTest {
  private final WebTestClient webTestClient;
  private final MenuService menuService;


  MenuControllerTest() {
    this.menuService = mock(MenuService.class);
    this.webTestClient = WebTestClient.bindToController(new MenuController(menuService)).build();
  }

  @Test
  @DisplayName("Agregar menú")
  void addMenu() {
    MenuRequestDTO menuRequest = new MenuRequestDTO("name");
    Menu menu = new Menu(1L, "name");
    when(menuService.addMenu(any(Menu.class))).thenReturn(menu);

    webTestClient.post()
      .uri("/api/menus")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(menuRequest)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(MenuResponseDTO.class)
      .value(menuResponseDTO -> {
        assertEquals(menuResponseDTO.getId(), menu.getId());
        assertEquals(menuResponseDTO.getName(), menu.getName());
        assertEquals(menuResponseDTO.getDishes(), menu.getDishes());
      });

    verify(menuService).addMenu(any(Menu.class));
  }

  @Test
  @DisplayName("Obtener menú por id")
  void getMenu() {
    Menu menu = new Menu(1L, "name");
    when(menuService.getMenuById(any(Long.class))).thenReturn(Optional.of(menu));

    webTestClient.get()
      .uri("/api/menus/{id}", 1L)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(MenuResponseDTO.class)
      .value(menuResponseDTO -> {
        assertEquals(menuResponseDTO.getId(), menu.getId());
        assertEquals(menuResponseDTO.getName(), menu.getName());
        assertEquals(menuResponseDTO.getDishes(), menu.getDishes());
      });

    verify(menuService).getMenuById(any(Long.class));
  }

  @Test
  @DisplayName("Obtener lista de menús")
  void getMenus() {
    when(menuService.getMenus()).thenReturn(getMenuList());

    webTestClient.get()
      .uri("/api/menus")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(MenuResponseDTO.class)
      .hasSize(3)
      .value(menuResponseDTOList -> {
        assertEquals(menuResponseDTOList.get(0).getName(), getMenuList().get(0).getName());
        assertEquals(menuResponseDTOList.get(1).getName(), getMenuList().get(1).getName());
        assertEquals(menuResponseDTOList.get(2).getName(), getMenuList().get(2).getName());
      });

    verify(menuService).getMenus();
  }

  @Test
  @DisplayName("Actualizar menú exitoso")
  void updateMenu() {
    MenuRequestDTO menuRequest = new MenuRequestDTO("name");
    Menu menu = new Menu(1L, "name");
    when(menuService.updateMenu(any(Long.class),any(Menu.class))).thenReturn(menu);

    webTestClient.put()
      .uri("/api/menus/{id}", 1L)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(menuRequest)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(MenuResponseDTO.class)
      .value(menuResponseDTO -> {
        assertEquals(menuResponseDTO.getId(), menu.getId());
        assertEquals(menuResponseDTO.getName(), menu.getName());
        assertEquals(menuResponseDTO.getDishes(), menu.getDishes());
      });

    verify(menuService).updateMenu(any(Long.class), any(Menu.class));
  }

  @Test
  @DisplayName("Actualizar menú con error")
  void updateMenuError() {
    MenuRequestDTO menuRequest = new MenuRequestDTO("name");
    Menu menu = new Menu(1L, "name");
    when(menuService.getMenuById(any(Long.class))).thenReturn(Optional.of(menu));
    when(menuService.updateMenu(any(Long.class),any(Menu.class))).thenThrow(new RuntimeException());

    webTestClient.put()
      .uri("/api/menus/{id}", 1L)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(menuRequest)
      .exchange()
      .expectStatus().is4xxClientError();

    verify(menuService).updateMenu(any(Long.class), any(Menu.class));
  }

  @Test
  @DisplayName("Eliminar menú")
  void deleteMenu() {
    doNothing().when(menuService).deleteMenu(any(Long.class));

    webTestClient.delete()
      .uri("/api/menus/{id}", 1L)
      .exchange()
      .expectStatus().isNoContent();

    verify(menuService).deleteMenu(any(Long.class));
  }

  private List<Menu> getMenuList() {
    return List.of(new Menu(1L, "A"),
      new Menu(2L, "B"),
      new Menu(3L, "C"));
  }
}