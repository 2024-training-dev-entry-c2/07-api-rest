package com.restaurant.restaurant.controllers;

import com.restaurant.restaurant.dtos.DishDTO;
import com.restaurant.restaurant.enums.DishType;
import com.restaurant.restaurant.services.DishService;
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

class DishControllerTest {

  private final WebTestClient webTestClient;
  private final DishService dishService;

  public DishControllerTest(){
    dishService = mock(DishService.class);
    webTestClient = WebTestClient.bindToController(new DishController(dishService)).build();
  }

  @Test
  @DisplayName("Get all dishes")
  void getAllDishes() {
    DishDTO dishDTO1 = new DishDTO(1L, "Pasta",  12.99, DishType.COMUN);
    DishDTO dishDTO2 = new DishDTO(2L, "Pizza", 15.49, DishType.POPULAR);

    when(dishService.findAll()).thenReturn(List.of(dishDTO1, dishDTO2));

    webTestClient
            .get()
            .uri("/api/dishes")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data[0].name").isEqualTo(dishDTO1.getName())
            .jsonPath("$.data[1].name").isEqualTo(dishDTO2.getName());
  }

  @Test
  @DisplayName("Get dish by ID")
  void getDishById() {
    DishDTO dishDTO = new DishDTO(1L, "Pasta", 12.99, DishType.COMUN);
    when(dishService.findById(1L)).thenReturn(dishDTO);

    webTestClient
            .get()
            .uri("/api/dishes/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.id").isEqualTo(dishDTO.getId().intValue())
            .jsonPath("$.data.name").isEqualTo(dishDTO.getName());
  }


  @Test
  @DisplayName("Create dish")
  void createDish() {
    DishDTO dishDTO = new DishDTO(1L, "Pasta", 12.99, DishType.COMUN);

    when(dishService.createDish(any(DishDTO.class))).thenReturn(dishDTO);

    webTestClient
            .post()
            .uri("/api/dishes")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(dishDTO)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.name").isEqualTo(dishDTO.getName())
            .jsonPath("$.data.price").isEqualTo(dishDTO.getPrice());
  }


  @Test
  @DisplayName("Update dish")
  void updateDish() {
    DishDTO dishDTO = new DishDTO(1L, "Pasta", 12.99, DishType.COMUN);
    DishDTO updatedDishDTO = new DishDTO(1L, "Pasta", 14.99, DishType.COMUN);

    when(dishService.updateDish(eq(1L), any(DishDTO.class))).thenReturn(updatedDishDTO);

    webTestClient
            .put()
            .uri("/api/dishes/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(updatedDishDTO)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.id").isEqualTo(updatedDishDTO.getId().intValue())
            .jsonPath("$.data.price").isEqualTo(updatedDishDTO.getPrice());
  }

  @Test
  @DisplayName("Delete dish")
  void deleteDish() {
    doNothing().when(dishService).deleteDish(1L);

    webTestClient
            .delete()
            .uri("/api/dishes/1")
            .exchange()
            .expectStatus().isNoContent();
  }
}