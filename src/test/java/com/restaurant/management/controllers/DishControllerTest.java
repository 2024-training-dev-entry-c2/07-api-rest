package com.restaurant.management.controllers;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Menu;
import com.restaurant.management.models.dto.DishRequestDTO;
import com.restaurant.management.models.dto.DishResponseDTO;
import com.restaurant.management.services.ClientService;
import com.restaurant.management.services.DishService;
import com.restaurant.management.services.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class DishControllerTest {
  private final WebTestClient webTestClient;
  private final DishService dishService;
  private final MenuService menuService;

  DishControllerTest() {
    this.dishService = mock(DishService.class);
    this.menuService = mock(MenuService.class);
    this.webTestClient = WebTestClient.bindToController(new DishController(dishService, menuService)).build();
  }

  @Test
  @DisplayName("Agregar plato exito")
  void addDish() {
    DishRequestDTO dishRequest = new DishRequestDTO("name", "description", 10f, 1L);
    Menu menu = new Menu(1L, "nameMenu");
    Dish dish = new Dish(1L, "name", "description", 10f, menu);

    when(menuService.addDishToMenu(any(Long.class), any(Dish.class))).thenReturn(dish);
    when(dishService.addDish(any(Dish.class))).thenReturn(dish);

    webTestClient.post()
      .uri("/api/platos")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(dishRequest)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(DishResponseDTO.class)
      .value(dishResponseDTO -> {
        assertEquals(dishResponseDTO.getId(), dish.getId());
        assertEquals(dishResponseDTO.getName(), dish.getName());
        assertEquals(dishResponseDTO.getDescription(), dish.getDescription());
        assertEquals(dishResponseDTO.getPrice(), dish.getPrice());
        assertEquals(dishResponseDTO.getState(), dish.getState().name());
        assertEquals(dishResponseDTO.getMenuId(), dish.getMenu().getId());
      });

    verify(menuService).addDishToMenu(any(Long.class), any(Dish.class));
    verify(dishService).addDish(any(Dish.class));
  }

  @Test
  @DisplayName("Agregar plato con error")
  void addDishError(){
    DishRequestDTO dishRequest = new DishRequestDTO("name", "description", 10f, 1L);
    Menu menu = new Menu(1L, "nameMenu");
    Dish dish = new Dish(1L, "name", "description", 10f, menu);

    when(menuService.addDishToMenu(any(Long.class), any(Dish.class))).thenThrow(new NoSuchElementException());

    webTestClient.post()
      .uri("/api/platos")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(dishRequest)
      .exchange()
      .expectStatus().isNotFound();

    verify(menuService).addDishToMenu(any(Long.class), any(Dish.class));
    verifyNoInteractions(dishService);
  }

  @Test
  @DisplayName("Obtener plato por id")
  void getDish() {
    Dish dish = new Dish(1L, "name", "description", 10f, new Menu(1L, "nameMenu"));

    when(dishService.getDishById(any(Long.class))).thenReturn(Optional.of(dish));

    webTestClient.get()
      .uri("/api/platos/{id}",1L)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(DishResponseDTO.class)
      .value(dishResponseDTO -> {
        assertEquals(dishResponseDTO.getId(), dish.getId());
        assertEquals(dishResponseDTO.getName(), dish.getName());
        assertEquals(dishResponseDTO.getDescription(), dish.getDescription());
        assertEquals(dishResponseDTO.getPrice(), dish.getPrice());
        assertEquals(dishResponseDTO.getState(), dish.getState().name());
        assertEquals(dishResponseDTO.getMenuId(), dish.getMenu().getId());
      });

    verify(dishService).getDishById(any(Long.class));
  }

  @Test
  @DisplayName("Obtener lista de platos")
  void getDishes() {
    when(dishService.getDishes()).thenReturn(getDishList());

    webTestClient.get()
      .uri("/api/platos")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(DishResponseDTO.class)
      .hasSize(3)
      .value(dishResponseDTOList -> {
        assertEquals(dishResponseDTOList.get(0).getName(), getDishList().get(0).getName());
        assertEquals(dishResponseDTOList.get(1).getName(), getDishList().get(1).getName());
        assertEquals(dishResponseDTOList.get(2).getName(), getDishList().get(2).getName());
      });

    verify(dishService).getDishes();
  }

  @Test
  @DisplayName("Actualizar plato exitoso")
  void updateDish() {
    DishRequestDTO dishRequest = new DishRequestDTO("name", "description", 10f, 1L);
    Menu menu = new Menu(1L, "nameMenu");
    Dish dish = new Dish(1L, "name", "description", 10f, menu);
    when(menuService.getMenuById(any(Long.class))).thenReturn(Optional.of(menu));
    when(dishService.updateDish(any(Long.class),any(Dish.class))).thenReturn(dish);

    webTestClient.put()
      .uri("/api/platos/{id}",1L)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(dishRequest)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(DishResponseDTO.class)
      .value(dishResponseDTO -> {
        assertEquals(dishResponseDTO.getId(), dish.getId());
        assertEquals(dishResponseDTO.getName(), dish.getName());
        assertEquals(dishResponseDTO.getDescription(), dish.getDescription());
        assertEquals(dishResponseDTO.getPrice(), dish.getPrice());
        assertEquals(dishResponseDTO.getState(), dish.getState().name());
        assertEquals(dishResponseDTO.getMenuId(), dish.getMenu().getId());
      });

    verify(dishService).updateDish(any(Long.class),any(Dish.class));
  }

  @Test
  @DisplayName("Actualizar plato con error")
  void updateDishError(){
    DishRequestDTO dishRequest = new DishRequestDTO("name", "description", 10f, 1L);
    Menu menu = new Menu(1L, "nameMenu");
    Dish dish = new Dish(1L, "name", "description", 10f, menu);
    when(menuService.getMenuById(any(Long.class))).thenReturn(Optional.of(menu));
    when(dishService.updateDish(any(Long.class),any(Dish.class))).thenThrow(new RuntimeException());

    webTestClient.put()
      .uri("/api/platos/{id}",1L)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(dishRequest)
      .exchange()
      .expectStatus().is4xxClientError();

    verify(dishService).updateDish(any(Long.class), any(Dish.class));
  }

  @Test
  @DisplayName("Eliminar plato")
  void deleteDish() {
    doNothing().when(dishService).deleteDish(any(Long.class));

    webTestClient.delete()
      .uri("/api/platos/{id}", 1L)
      .exchange()
      .expectStatus().isNoContent();

    verify(dishService).deleteDish(any(Long.class));
  }

  private List<Dish> getDishList() {
    return List.of(new Dish(1L, "A", "Ve", 10f, new Menu(1L, "nameMenu")),
      new Dish(2L, "B", "Ce", 20f, new Menu(1L, "nameMenu")),
      new Dish(3L, "C", "De", 30f, new Menu(1L, "nameMenu")));
  }
}