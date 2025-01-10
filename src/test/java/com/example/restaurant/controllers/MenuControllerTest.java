package com.example.restaurant.controllers;

import com.example.restaurant.models.dto.dish.DishResponseDTO;
import com.example.restaurant.models.dto.menu.MenuRequestDTO;
import com.example.restaurant.models.dto.menu.MenuResponseDTO;
import com.example.restaurant.services.menu.MenuCommandHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuControllerTest {

  private final MenuCommandHandler menuService;
  private final WebTestClient webTestClient;

  MenuControllerTest() {
    menuService = mock(MenuCommandHandler.class);
    this.webTestClient = WebTestClient.bindToController(new MenuController(menuService)).build();
  }

  @Test
  @DisplayName("Crear un menu")
  void createMenu() {

    MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
    menuRequestDTO.setName("Menu 1");
    menuRequestDTO.setDescription("Description 1");
    menuRequestDTO.setDishIds(getDishList1().stream().map(DishResponseDTO::getCustomerId).toList());

    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
    menuResponseDTO.setName("Menu 1");
    menuResponseDTO.setDescription("Description 1");
    menuResponseDTO.setDishes(getDishList1());

    when(menuService.createMenu(any(MenuRequestDTO.class))).thenReturn(menuResponseDTO);

    webTestClient
            .post()
            .uri("/menus")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(menuRequestDTO)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody(MenuResponseDTO.class)
            .value(d -> {
              assertEquals(menuResponseDTO.getName(), d.getName());
              assertEquals(menuResponseDTO.getDescription(), d.getDescription());
            });

    Mockito.verify(menuService).createMenu(any(MenuRequestDTO.class));
  }

  @Test
  @DisplayName("Obtener un menú por id")
  void getMenuById() {

    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
    menuResponseDTO.setMenuId(1L);
    menuResponseDTO.setName("Menu 1");
    menuResponseDTO.setDescription("Description 1");
    menuResponseDTO.setDishes(getDishList1());

    when(menuService.getMenuById(anyLong())).thenReturn(menuResponseDTO);

    webTestClient
            .get()
            .uri("/menus/{id}", 1L)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody(MenuResponseDTO.class)
            .value(d -> {
              assertEquals(menuResponseDTO.getName(), d.getName());
              assertEquals(menuResponseDTO.getDescription(), d.getDescription());
              assertEquals(menuResponseDTO.getDishes(), d.getDishes());
            });

    Mockito.verify(menuService).getMenuById(anyLong());
  }

  @Test
  @DisplayName("Actualizar un menú")
  void updateMenu() {

    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
    menuResponseDTO.setMenuId(1L);
    menuResponseDTO.setName("Menu 1");
    menuResponseDTO.setDescription("Description 1");
    menuResponseDTO.setDishes(getDishList1());

    MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
    menuRequestDTO.setName("Menu 1");
    menuRequestDTO.setDescription("Description 1");
    menuRequestDTO.setDishIds(getDishList1().stream().map(DishResponseDTO::getCustomerId).toList());

    when(menuService.updateMenu(anyLong(), any(MenuRequestDTO.class))).thenReturn(menuResponseDTO);

    webTestClient
            .put()
            .uri("/menus/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(menuRequestDTO)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody(MenuResponseDTO.class)
            .value(d -> {
              assertEquals(menuResponseDTO.getName(), d.getName());
              assertEquals(menuResponseDTO.getDescription(), d.getDescription());
              assertEquals(menuResponseDTO.getDishes(), d.getDishes());
            });

    Mockito.verify(menuService).updateMenu(anyLong(), any(MenuRequestDTO.class));
  }

  @Test
  @DisplayName("Eliminar un menú")
  void deleteMenu() {

    doNothing().when(menuService).deleteMenu(anyLong());

    webTestClient
            .delete()
            .uri("/menus/{id}", 1L)
            .exchange()
            .expectStatus()
            .isNoContent();

    Mockito.verify(menuService).deleteMenu(anyLong());
  }

  @Test
  @DisplayName("Listar todos los menús")
  void listMenus() {

    when(menuService.listMenus()).thenReturn(getMenuList());

    webTestClient
            .get()
            .uri("/menus")
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(MenuResponseDTO.class)
            .value(d -> {
              assertEquals(getMenuList().size(), d.size());
            });

    Mockito.verify(menuService).listMenus();
  }

  public List<MenuResponseDTO> getMenuList() {

    MenuResponseDTO menu1 = new MenuResponseDTO();
    menu1.setMenuId(1L);
    menu1.setName("Productos del mar");
    menu1.setDescription("Menu de productos del mar de la región");
    menu1.setDishes(getDishList1());


    MenuResponseDTO menu2 = new MenuResponseDTO();
    menu2.setMenuId(2L);
    menu2.setName("Postres");
    menu2.setDescription("Menu de postres");
    menu2.setDishes(getDishList2());

    return List.of(menu1, menu2);
  }

  public List<DishResponseDTO> getDishList1() {
    DishResponseDTO dish1 = new DishResponseDTO();
    dish1.setCustomerId(1L);
    dish1.setName("Ceviche de camarones");
    dish1.setPrice(12000.0f);

    DishResponseDTO dish2 = new DishResponseDTO();
    dish2.setCustomerId(2L);
    dish2.setName("Cangrejo hervido");
    dish2.setPrice(10000.0f);

    return List.of(dish1, dish2);
  }

  public List<DishResponseDTO> getDishList2() {
    DishResponseDTO dish1 = new DishResponseDTO();
    dish1.setCustomerId(3L);
    dish1.setName("Torta de chocolate");
    dish1.setPrice(12500.0f);

    DishResponseDTO dish2 = new DishResponseDTO();
    dish2.setCustomerId(4L);
    dish2.setName("Gelatina de fresa");
    dish2.setPrice(11000.0f);

    return List.of(dish1, dish2);
  }

}
