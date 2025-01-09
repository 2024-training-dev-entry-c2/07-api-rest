package com.example.demo.controllers;

import com.example.demo.DTO.dishfood.DishfoodRequestDTO;
import com.example.demo.DTO.dishfood.DishfoodResponseDTO;
import com.example.demo.services.DishFoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(DishfoodController.class)
class DishfoodControllerTest {
    @MockBean
    private DishFoodService dishFoodService;

    @Autowired
    private WebTestClient webTestClient;

    private DishfoodRequestDTO dishfoodRequestDTO;
    private DishfoodResponseDTO dishfoodResponseDTO;

    @BeforeEach
    void setUp() {
        dishfoodRequestDTO = new DishfoodRequestDTO(
                "Pizza",
                12.99,
                true,
                1L
        );

        dishfoodResponseDTO = DishfoodResponseDTO.builder()
                .id(1L)
                .name("Pizza")
                .price(12.99)
                .isPopular(true)
                .menu("Main Menu")
                .build();
    }

    @DisplayName("Crear un nuevo plato")
    @Test
    void createDishfood() {
        when(dishFoodService.createDishFood(any(DishfoodRequestDTO.class))).thenReturn(dishfoodResponseDTO);

        webTestClient.post()
                .uri("/dishfoods")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dishfoodRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DishfoodResponseDTO.class)
                .value(response -> {
                    assertEquals(dishfoodResponseDTO.getId(),response.getId());
                    assertEquals(dishfoodResponseDTO.getName(),response.getName());
                    assertEquals(dishfoodResponseDTO.getPrice(),response.getPrice());
                    assertEquals(dishfoodResponseDTO.getIsPopular(),response.getIsPopular());
                    assertEquals(dishfoodResponseDTO.getMenu(),response.getMenu());
                });

        verify(dishFoodService).createDishFood(any(DishfoodRequestDTO.class));
    }

    @Test
    void getDishfoodById() {
        when(dishFoodService.getDishfoodById(anyLong())).thenReturn(dishfoodResponseDTO);
        webTestClient.get()
                .uri("/dishfoods/{id}",1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DishfoodResponseDTO.class)
                .value(response -> {
                    assertEquals(dishfoodResponseDTO.getId(),response.getId());
                    assertEquals(dishfoodResponseDTO.getName(),response.getName());
                    assertEquals(dishfoodResponseDTO.getPrice(),response.getPrice());
                    assertEquals(dishfoodResponseDTO.getIsPopular(),response.getIsPopular());
                    assertEquals(dishfoodResponseDTO.getMenu(),response.getMenu());
                });
                verify(dishFoodService).getDishfoodById(anyLong());
    }

    @Test
    void getAllDishfoods() {
        when(dishFoodService.getAllDishfoods()).thenReturn(getDishfoodList());
        webTestClient.get()
                .uri("/dishfoods")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(DishfoodResponseDTO.class)
                .hasSize(3)
                .value(response -> {
                    assertEquals("Pizza", response.get(0).getName());
                    assertEquals("Hamburguer", response.get(1).getName());
                    assertEquals("caracoles", response.get(2).getName());
                });
        verify(dishFoodService).getAllDishfoods();
    }

    @Test
    void updateDishfood() {
        when(dishFoodService.updateDishfood(anyLong(),any(DishfoodRequestDTO.class))).thenReturn(dishfoodResponseDTO);

        webTestClient.put()
                .uri("/dishfoods/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dishfoodRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DishfoodResponseDTO.class)
                .value(response -> {
                    assertEquals(dishfoodResponseDTO.getId(),response.getId());
                    assertEquals(dishfoodResponseDTO.getName(),response.getName());
                    assertEquals(dishfoodResponseDTO.getPrice(),response.getPrice());
                    assertEquals(dishfoodResponseDTO.getIsPopular(),response.getIsPopular());
                    assertEquals(dishfoodResponseDTO.getMenu(),response.getMenu());
                });
        verify(dishFoodService).updateDishfood(anyLong(),any(DishfoodRequestDTO.class));

    }

    @Test
    void deleteDishfood() {
        doNothing().when(dishFoodService).deleteDishfood(anyLong());
        webTestClient.delete()
                .uri("/dishfoods/{id}",1L)
                .exchange()
                .expectStatus().isNoContent();
        verify(dishFoodService).deleteDishfood(anyLong());
    }

    private List<DishfoodResponseDTO> getDishfoodList() {
        return List.of(
                DishfoodResponseDTO.builder()
                        .id(1L)
                        .name("Pizza")
                        .price(12.99)
                        .isPopular(true)
                        .menu("Main Menu")
                        .build(),
                DishfoodResponseDTO.builder()
                        .id(2L)
                        .name("Hamburguer")
                        .price(15.99)
                        .isPopular(false)
                        .menu("Main Menu")
                        .build(),
                DishfoodResponseDTO.builder()
                        .id(3L)
                        .name("caracoles")
                        .price(10.99)
                        .isPopular(false)
                        .menu("Main Menu")
                        .build()

        );
    }
}