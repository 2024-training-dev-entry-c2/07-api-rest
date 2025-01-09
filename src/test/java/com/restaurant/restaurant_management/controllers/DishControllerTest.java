package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.dto.DishRequestDTO;
import com.restaurant.restaurant_management.dto.DishResponseDTO;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.models.Menu;
import com.restaurant.restaurant_management.services.DishService;
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

class DishControllerTest {
  private final DishService dishService;
  private final MenuService menuService;
  private final WebTestClient webTestClient;

  private Dish dish;
  private DishRequestDTO dishRequestDTO;
  private DishResponseDTO dishResponseDTO;
  private Menu menu;
  private List<Dish> dishes;
  private List<DishResponseDTO> dishesResponseDTO;

  public DishControllerTest() {
    dishService = mock(DishService.class);
    menuService = mock(MenuService.class);
    webTestClient = WebTestClient.bindToController(new DishController(dishService, menuService)).build();
  }

  @BeforeEach
  void setUp() {
    menu = new Menu(1, "Menu 1", "Descripcion del menu 1", true);
    dish = new Dish(1, "Pollo", "Ocho presas", 45000, true, true, menu);
    dishRequestDTO = new DishRequestDTO("Pollo", "Ocho presas", 45000, true, 1, true);
    dishResponseDTO = new DishResponseDTO(1, "Pollo", "Ocho presas", 45000, true, true);
    dishes = List.of(
      new Dish(1, "Pollo", "Ocho presas", 55400, true, true, menu),
      new Dish(2, "Nuggets", "Con papas fritas y salsa de manzana", 21900, true, true, menu),
      new Dish(3, "Combo tradicional", "Con fríjoles y ensalada", 27900, true, true, menu)

    );
    dishesResponseDTO = List.of(
      new DishResponseDTO(1, "Pollo", "Ocho presas", 55400, true, true),
      new DishResponseDTO(2, "Nuggets", "Con papas fritas y salsa de manzana", 21900, true, true),
      new DishResponseDTO(3, "Combo tradicional", "Con fríjoles y ensalada", 27900, true, true)
    );
  }

  @Test
  @DisplayName("Save dish")
  void saveDish() {
    when(menuService.getMenu(any(Integer.class))).thenReturn(Optional.of(menu));
    when(dishService.saveDish(any(Dish.class))).thenReturn(dish);

    webTestClient.post()
      .uri("/api/dish")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(dishRequestDTO)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(DishResponseDTO.class)
      .value(dish1->{
        assertEquals(dishResponseDTO.getId(), dish1.getId());
        assertEquals(dishResponseDTO.getDishName(), dish1.getDishName());
        assertEquals(dishResponseDTO.getDescription(), dish1.getDescription());
        assertEquals(dishResponseDTO.getBasePrice(), dish1.getBasePrice());
        assertEquals(dishResponseDTO.getIsPopular(), dish1.getIsPopular());
        assertEquals(dishResponseDTO.getActive(), dish1.getActive());
      });

    Mockito.verify(menuService).getMenu(any(Integer.class));
    Mockito.verify(dishService).saveDish(any(Dish.class));
  }

  @Test
  @DisplayName("Save dish - No menu")
  void saveDish_NoMenu() {
    when(menuService.getMenu(any(Integer.class))).thenReturn(Optional.empty());
    webTestClient.post()
      .uri("/api/dish")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(dishRequestDTO)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody().isEmpty();

    Mockito.verify(dishService, Mockito.never()).saveDish(any(Dish.class));
    Mockito.verify(menuService).getMenu(any(Integer.class));
  }

  @Test
  @DisplayName("Get dish")
  void getDish() {
    when(dishService.getDish(any(Integer.class))).thenReturn(Optional.of(dish));

    webTestClient.get()
      .uri("/api/dish/{id}", 1)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(DishResponseDTO.class)
      .value(dish1->{
        assertEquals(dishResponseDTO.getId(), dish1.getId());
        assertEquals(dishResponseDTO.getDishName(), dish1.getDishName());
        assertEquals(dishResponseDTO.getDescription(), dish1.getDescription());
        assertEquals(dishResponseDTO.getBasePrice(), dish1.getBasePrice());
        assertEquals(dishResponseDTO.getIsPopular(), dish1.getIsPopular());
        assertEquals(dishResponseDTO.getActive(), dish1.getActive());
      });

    Mockito.verify(dishService).getDish(any(Integer.class));
  }

  @Test
  @DisplayName("Get dishes")
  void getDishes() {
    when(dishService.listDishes()).thenReturn(dishes);

    webTestClient.get()
      .uri("/api/dish")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(DishResponseDTO.class)
      .hasSize(3)
      .value(dishes1->{
        assertEquals(dishesResponseDTO.size(), dishes1.size());
        assertEquals("Pollo", dishes1.get(0).getDishName());
        assertEquals("Nuggets", dishes1.get(1).getDishName());
        assertEquals("Combo tradicional", dishes1.get(2).getDishName());
      });

    Mockito.verify(dishService).listDishes();
  }

  @Test
  @DisplayName("Get dishes by menu id and active")
  void getDishesByMenuIdAndActive() {
    when(dishService.listDishesByMenuIdAndActive(any(Integer.class))).thenReturn(dishes);

    webTestClient.get()
      .uri("/api/dish/menu/{menuId}", 1)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(DishResponseDTO.class)
      .hasSize(3)
      .value(dishes1->{
        assertEquals(dishesResponseDTO.size(), dishes1.size());
        assertEquals("Pollo", dishes1.get(0).getDishName());
        assertEquals("Nuggets", dishes1.get(1).getDishName());
        assertEquals("Combo tradicional", dishes1.get(2).getDishName());
      });

    Mockito.verify(dishService).listDishesByMenuIdAndActive(any(Integer.class));
  }

  @Test
  @DisplayName("Update dish")
  void updateDish() {
    when(menuService.getMenu(any(Integer.class))).thenReturn(Optional.of(menu));
    when(dishService.updateDish(any(Integer.class), any(Dish.class))).thenReturn(dish);

    webTestClient.put()
      .uri("/api/dish/{id}", 1)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(dishRequestDTO)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(DishResponseDTO.class)
      .value(dish1->{
        assertEquals(dishResponseDTO.getId(), dish1.getId());
        assertEquals(dishResponseDTO.getDishName(), dish1.getDishName());
        assertEquals(dishResponseDTO.getDescription(), dish1.getDescription());
        assertEquals(dishResponseDTO.getBasePrice(), dish1.getBasePrice());
        assertEquals(dishResponseDTO.getIsPopular(), dish1.getIsPopular());
        assertEquals(dishResponseDTO.getActive(), dish1.getActive());
      });

    Mockito.verify(menuService).getMenu(any(Integer.class));
    Mockito.verify(dishService).updateDish(any(Integer.class), any(Dish.class));
  }

  @Test
  @DisplayName("Update dish - Dish not found")
  void updateDish_NoMenu() {
    when(menuService.getMenu(any(Integer.class))).thenReturn(Optional.empty());
    webTestClient.put()
      .uri("/api/dish/{id}", 1)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(dishRequestDTO)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody().isEmpty();

    Mockito.verify(dishService, Mockito.never()).updateDish(any(Integer.class), any(Dish.class));
    Mockito.verify(menuService).getMenu(any(Integer.class));
  }

}