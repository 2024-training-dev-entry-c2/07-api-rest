package com.api_restaurant.controllers;

import com.api_restaurant.dto.dish.DishRequestDTO;
import com.api_restaurant.dto.dish.DishResponseDTO;
import com.api_restaurant.models.Dish;
import com.api_restaurant.repositories.MenuRepository;
import com.api_restaurant.services.DishService;
import com.api_restaurant.utils.mapper.DishDtoConvert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DishControllerTest {
    private final WebTestClient webTestClient;
    private final DishService dishService;
    private final MenuRepository menuRepository;

    public DishControllerTest() {
        dishService = mock(DishService.class);
        menuRepository = mock(MenuRepository.class);
        webTestClient = WebTestClient.bindToController(new DishController(dishService, menuRepository)).build();
    }

    @Test
    @DisplayName("Agregar Plato")
    void addDish() {
        DishRequestDTO dishRequestDTO = new DishRequestDTO();
        dishRequestDTO.setName("Plato 1");
        dishRequestDTO.setDescription("Descripción del Plato 1");
        dishRequestDTO.setPrice(10.0);
        dishRequestDTO.setMenuId(1L);

        Dish dish = new Dish("Plato 1", "Descripción del Plato 1", 10.0, null);
        dish.setId(1L); // Ensure the dish has an ID
        DishResponseDTO dishResponseDTO = new DishResponseDTO();
        dishResponseDTO.setId(1L);
        dishResponseDTO.setName("Plato 1");
        dishResponseDTO.setDescription("Descripción del Plato 1");
        dishResponseDTO.setPrice(10.0);

        when(dishService.addDish(any(Dish.class), any(Long.class))).thenReturn(dish);
        when(menuRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));

        webTestClient.post()
                .uri("/dish")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dishRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(DishResponseDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response.getId(), dishResponseDTO.getId());
                    assertEquals(response.getName(), dishResponseDTO.getName());
                    assertEquals(response.getDescription(), dishResponseDTO.getDescription());
                    assertEquals(response.getPrice(), dishResponseDTO.getPrice());
                });
        Mockito.verify(dishService).addDish(any(Dish.class), any(Long.class));
    }

    @Test
    @DisplayName("Obtener Plato por ID")
    void getDish() {
        Dish dish = new Dish("Plato 1", "Descripción del Plato 1", 10.0, null);
        dish.setId(1L);

        DishResponseDTO dishResponseDTO = new DishResponseDTO();
        dishResponseDTO.setId(1L);
        dishResponseDTO.setName("Plato 1");
        dishResponseDTO.setDescription("Descripción del Plato 1");
        dishResponseDTO.setPrice(10.0);

        when(dishService.getDish(1L)).thenReturn(Optional.of(dish));

        webTestClient.get()
                .uri("/dish/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(DishResponseDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response.getId(), dishResponseDTO.getId());
                    assertEquals(response.getName(), dishResponseDTO.getName());
                    assertEquals(response.getDescription(), dishResponseDTO.getDescription());
                    assertEquals(response.getPrice(), dishResponseDTO.getPrice());
                });
        Mockito.verify(dishService).getDish(1L);
    }

    @Test
    @DisplayName("Obtener Plato por ID - No Encontrado")
    void getDishNotFound() {
        when(dishService.getDish(1L)).thenReturn(Optional.empty());

        webTestClient.get()
                .uri("/dish/1")
                .exchange()
                .expectStatus().isNotFound();
        Mockito.verify(dishService).getDish(1L);
    }

    @Test
    @DisplayName("Obtener Todos los Platos")
    void getDishes() {
        Dish dish1 = new Dish("Plato 1", "Descripción del Plato 1", 10.0, null);
        dish1.setId(1L);
        Dish dish2 = new Dish("Plato 2", "Descripción del Plato 2", 15.0, null);
        dish2.setId(2L);

        DishResponseDTO dishResponseDTO1 = new DishResponseDTO();
        dishResponseDTO1.setId(1L);
        dishResponseDTO1.setName("Plato 1");
        dishResponseDTO1.setDescription("Descripción del Plato 1");
        dishResponseDTO1.setPrice(10.0);

        DishResponseDTO dishResponseDTO2 = new DishResponseDTO();
        dishResponseDTO2.setId(2L);
        dishResponseDTO2.setName("Plato 2");
        dishResponseDTO2.setDescription("Descripción del Plato 2");
        dishResponseDTO2.setPrice(15.0);

        when(dishService.getDishes()).thenReturn(List.of(dish1, dish2));

        webTestClient.get()
                .uri("/dish")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DishResponseDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(2, response.size());
                    assertEquals(response.get(0).getId(), dishResponseDTO1.getId());
                    assertEquals(response.get(0).getName(), dishResponseDTO1.getName());
                    assertEquals(response.get(0).getDescription(), dishResponseDTO1.getDescription());
                    assertEquals(response.get(0).getPrice(), dishResponseDTO1.getPrice());
                    assertEquals(response.get(1).getId(), dishResponseDTO2.getId());
                    assertEquals(response.get(1).getName(), dishResponseDTO2.getName());
                    assertEquals(response.get(1).getDescription(), dishResponseDTO2.getDescription());
                    assertEquals(response.get(1).getPrice(), dishResponseDTO2.getPrice());
                });
        Mockito.verify(dishService).getDishes();
    }

    @Test
    @DisplayName("Actualizar Plato")
    void updateDish() {
        DishRequestDTO dishRequestDTO = new DishRequestDTO();
        dishRequestDTO.setName("Plato Actualizado");
        dishRequestDTO.setDescription("Descripción Actualizada");
        dishRequestDTO.setPrice(20.0);
        dishRequestDTO.setMenuId(1L);

        Dish dish = new Dish("Plato Actualizado", "Descripción Actualizada", 20.0, null);
        dish.setId(1L);

        when(dishService.updateDish(any(Long.class), any(Dish.class))).thenReturn(dish);

        webTestClient.put()
                .uri("/dish/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dishRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response, "Plato actualizado exitosamente");
                });
        Mockito.verify(dishService).updateDish(any(Long.class), any(Dish.class));
    }

    @Test
    @DisplayName("Actualizar Plato - No Encontrado")
    void updateDishNotFound() {
        DishRequestDTO dishRequestDTO = new DishRequestDTO();
        dishRequestDTO.setName("Plato Actualizado");
        dishRequestDTO.setDescription("Descripción Actualizada");
        dishRequestDTO.setPrice(20.0);
        dishRequestDTO.setMenuId(1L);

        when(dishService.updateDish(any(Long.class), any(Dish.class)))
                .thenThrow(new RuntimeException("Dish with id 1 could not be updated"));

        webTestClient.put()
                .uri("/dish/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dishRequestDTO)
                .exchange()
                .expectStatus().isNotFound();
        Mockito.verify(dishService).updateDish(any(Long.class), any(Dish.class));
    }

    @Test
    @DisplayName("Eliminar Plato")
    void deleteDish() {
        Mockito.doNothing().when(dishService).deleteDish(1L);

        webTestClient.delete()
                .uri("/dish/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response, "Plato eliminado exitosamente");
                });
        Mockito.verify(dishService).deleteDish(1L);
    }

    @Test
    @DisplayName("Eliminar Plato - No Encontrado")
    void deleteDishNotFound() {
        Mockito.doThrow(new RuntimeException("Dish with id 1 not found")).when(dishService).deleteDish(1L);

        webTestClient.delete()
                .uri("/dish/1")
                .exchange()
                .expectStatus().isNotFound();
        Mockito.verify(dishService).deleteDish(1L);
    }
}