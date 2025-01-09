package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.dto.MenuRequestDTO;
import com.restaurant.restaurant_management.dto.MenuResponseDTO;
import com.restaurant.restaurant_management.models.Menu;
import com.restaurant.restaurant_management.services.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuControllerTest {

  private final MenuService menuService;
  private final WebTestClient webTestClient;

  private Menu menu;
  private MenuRequestDTO menuRequestDTO;
  private MenuResponseDTO menuResponseDTO;
  private List<MenuResponseDTO> menuResponseDTOList;
  private List<Menu> menuList;

  public MenuControllerTest() {
    menuService = mock(MenuService.class);
    webTestClient = WebTestClient.bindToController(new MenuController(menuService)).build();
  }

  @BeforeEach
  void setUp() {
    menu = new Menu(1, "Menu 1", "Descripcion del menu 1", true);
    menuRequestDTO = new MenuRequestDTO("Menu 1", "Descripcion del menu 1", true);
    menuResponseDTO = new MenuResponseDTO(1, "Menu 1", "Descripcion del menu 1", true);
    menuResponseDTOList = List.of(
      new MenuResponseDTO(1, "Menu 1", "Descripcion del menu 1", true),
      new MenuResponseDTO(2, "Menu 2", "Descripcion del menu 2", true),
      new MenuResponseDTO(3, "Menu 3", "Descripcion del menu 3", true)
    );
    menuList = List.of(
      new Menu(1, "Menu 1", "Descripcion del menu 1", true),
      new Menu(2, "Menu 2", "Descripcion del menu 2", true),
      new Menu(3, "Menu 3", "Descripcion del menu 3", true)
    );
  }

  @Test
  @DisplayName("Save menu")
  void saveMenu() {
    when(menuService.saveMenu(any(Menu.class))).thenReturn(menu);

    webTestClient.post()
      .uri("/api/menu")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(menuRequestDTO)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(MenuResponseDTO.class)
      .value(menu1->{
        assertEquals(menuResponseDTO.getId(), menu1.getId());
        assertEquals(menuResponseDTO.getMenuName(), menu1.getMenuName());
        assertEquals(menuResponseDTO.getDescription(), menu1.getDescription());
        assertEquals(menuResponseDTO.getActive(), menu1.getActive());
      });

    Mockito.verify(menuService).saveMenu(any(Menu.class));
  }

  @Test
  @DisplayName("Get menu")
  void getMenu() {
    when(menuService.getMenu(any(Integer.class))).thenReturn(Optional.of(menu));

    webTestClient.get()
      .uri("/api/menu/{id}", 1)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(MenuResponseDTO.class)
      .value(menu1->{
        assertEquals(menuResponseDTO.getId(), menu1.getId());
        assertEquals(menuResponseDTO.getMenuName(), menu1.getMenuName());
        assertEquals(menuResponseDTO.getDescription(), menu1.getDescription());
        assertEquals(menuResponseDTO.getActive(), menu1.getActive());
      });

    Mockito.verify(menuService).getMenu(any(Integer.class));
  }

  @Test
  @DisplayName("List menus")
  void getMenus() {
    when(menuService.listMenus()).thenReturn(menuList);

    webTestClient.get()
      .uri("/api/menu")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(MenuResponseDTO.class)
      .hasSize(3)
      .value(menus->{
        assertEquals(menuResponseDTOList.size(), menus.size());
        assertEquals("Menu 1", menus.get(0).getMenuName());
        assertEquals("Menu 2", menus.get(1).getMenuName());
        assertEquals("Menu 3", menus.get(2).getMenuName());
      });

    Mockito.verify(menuService).listMenus();
  }

  @Test
  @DisplayName("Get active menus")
  void getActiveMenus() {
    when(menuService.listActiveMenus()).thenReturn(menuList);

    webTestClient.get()
      .uri("/api/menu/active")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(MenuResponseDTO.class)
      .hasSize(3)
      .value(menus->{
        assertEquals(menuResponseDTOList.size(), menus.size());
        assertEquals("Menu 1", menus.get(0).getMenuName());
        assertEquals("Menu 2", menus.get(1).getMenuName());
        assertEquals("Menu 3", menus.get(2).getMenuName());
      });

    Mockito.verify(menuService).listActiveMenus();
  }

  @Test
  @DisplayName("Update menu")
  void updateMenu() {
    when(menuService.updateMenu(any(Integer.class), any(Menu.class))).thenReturn(menu);

    webTestClient.put()
      .uri("/api/menu/{id}", 1)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(menuRequestDTO)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(MenuResponseDTO.class)
      .value(menu1->{
        assertEquals(menuResponseDTO.getId(), menu1.getId());
        assertEquals(menuResponseDTO.getMenuName(), menu1.getMenuName());
        assertEquals(menuResponseDTO.getDescription(), menu1.getDescription());
        assertEquals(menuResponseDTO.getActive(), menu1.getActive());
      });
    Mockito.verify(menuService).updateMenu(any(Integer.class), any(Menu.class));
  }

  @Test
  @DisplayName("Update menu - Menu not found")
  void updateMenu_NotFound() {
    when(menuService.updateMenu(any(Integer.class), any(Menu.class)))
      .thenThrow(new RuntimeException("Menu not found"));

    webTestClient.put()
      .uri("/api/menu/{id}", 1)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(menuRequestDTO)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody().isEmpty();

    Mockito.verify(menuService).updateMenu(any(Integer.class), any(Menu.class));
  }

}