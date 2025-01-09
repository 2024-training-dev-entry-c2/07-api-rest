package com.example.restaurant.controllers;

import com.example.restaurant.mappers.DishMapper;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.dto.dish.DishRequestDTO;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import com.example.restaurant.services.dish.DishCommandHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DishControllerTest {

  private final DishCommandHandler dishService;
  private final DishMapper dishMapper;
  private final WebTestClient webTestClient;

  public DishControllerTest() {
    dishMapper = mock(DishMapper.class);
    dishService = mock(DishCommandHandler.class);
    this.webTestClient = WebTestClient.bindToController(new DishController(dishService, dishMapper)).build();
  }

  @Test
  @DisplayName("Crear un plato")
  void createDish() {

    DishRequestDTO dishRequestDTO = new DishRequestDTO();
    dishRequestDTO.setName("Dish 1");
    dishRequestDTO.setPrice(10.0f);

    DishResponseDTO dishResponseDTO = new DishResponseDTO();
    dishResponseDTO.setName("Dish 1");
    dishResponseDTO.setPrice(10.0f);

    when(dishService.createDish(any(DishRequestDTO.class))).thenReturn(dishResponseDTO);

    webTestClient
            .post()
            .uri("/dishes")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(dishRequestDTO)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(DishResponseDTO.class)
            .value(d -> {
              assertEquals(dishResponseDTO.getName(), d.getName());
              assertEquals(dishResponseDTO.getPrice(), d.getPrice());
            });

    Mockito.verify(dishService).createDish(any(DishRequestDTO.class));
  }

  @Test
  @DisplayName("Actualizar el precio y nombre de un plato")
  void updateDish() {
    DishRequestDTO dishRequestDTO = new DishRequestDTO();
    dishRequestDTO.setName("Burger");
    dishRequestDTO.setPrice(10.0f);

    DishResponseDTO dishResponseDTO = new DishResponseDTO();
    dishResponseDTO.setName("Burger");
    dishResponseDTO.setPrice(10.0f);

    when(dishService.updateDish(any(Long.class), any(DishRequestDTO.class))).thenReturn(dishResponseDTO);

    webTestClient
            .put()
            .uri("/dishes/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(dishRequestDTO)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(DishResponseDTO.class)
            .value(d -> {
              assertEquals(dishResponseDTO.getName(), d.getName());
              assertEquals(dishResponseDTO.getPrice(), d.getPrice());
            });

    Mockito.verify(dishService).updateDish(any(Long.class), any(DishRequestDTO.class));
  }
//  TODO: Uncomment this test

  @Test
  @DisplayName("Eliminar un plato por id")
  void deleteDish() {

    doNothing().when(dishService).deleteDish(anyLong());

    webTestClient
            .delete()
            .uri("/dishes/{id}", 1L)
            .exchange()
            .expectStatus().isNoContent();

    Mockito.verify(dishService).deleteDish(any(Long.class));
  }

  @Test
  @DisplayName("Obtener un plato por id")
  void getDishById() {

    DishResponseDTO dishResponseDTO = new DishResponseDTO();
    dishResponseDTO.setName("Burger");
    dishResponseDTO.setPrice(10.0f);

    when(dishService.getDishById(anyLong())).thenReturn(dishResponseDTO);

    webTestClient
            .get()
            .uri("/dishes/1")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(DishResponseDTO.class)
            .value(d -> {
              assertEquals(dishResponseDTO.getName(), d.getName());
              assertEquals(dishResponseDTO.getPrice(), d.getPrice());
            });

    Mockito.verify(dishService).getDishById(any(Long.class));
  }

  @Test
  @DisplayName("Listar todos los platos")
  void listDishes() {

    when(dishService.listDishes()).thenReturn((getDishList()));

    webTestClient
            .get()
            .uri("/dishes")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(DishResponseDTO.class)
            .hasSize(2)
            .value(d -> {
              assertEquals("Burger", d.get(0).getName());
              assertEquals(10L, d.get(0).getPrice());
              assertEquals("Chesse Burger", d.get(1).getName());
              assertEquals(12L, d.get(1).getPrice());
            });

    Mockito.verify(dishService).listDishes();
  }

  private List<DishResponseDTO> getDishList() {
    DishResponseDTO dish1 = new DishResponseDTO();
    dish1.setName("Burger");
    dish1.setPrice(10.0f);

    DishResponseDTO dish2 = new DishResponseDTO();
    dish2.setName("Chesse Burger");
    dish2.setPrice(12.0f);

    return List.of(dish1, dish2);
  }
}