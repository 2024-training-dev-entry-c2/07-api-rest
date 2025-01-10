package com.api_restaurant.controllers;

import com.api_restaurant.dto.menu.MenuRequestDTO;
import com.api_restaurant.dto.menu.MenuResponseDTO;
import com.api_restaurant.models.Menu;
import com.api_restaurant.services.MenuService;
import com.api_restaurant.utils.mapper.MenuDtoConvert;
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

class MenuControllerTest {
    private final WebTestClient webTestClient;
    private final MenuService menuService;
    private final MenuDtoConvert menuDtoConvert;

    public MenuControllerTest() {
        menuService = mock(MenuService.class);
        menuDtoConvert = mock(MenuDtoConvert.class);
        webTestClient = WebTestClient.bindToController(new MenuController(menuService, menuDtoConvert)).build();
    }

    @Test
    @DisplayName("Agregar Menu")
    void addMenu() {
        MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
        menuRequestDTO.setName("Menu 1");

        Menu menu = new Menu("Menu 1");
        menu.setId(1L);

        MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
        menuResponseDTO.setId(1L);
        menuResponseDTO.setName("Menu 1");

        when(menuService.addMenu(any(Menu.class))).thenReturn(menu);
        when(menuDtoConvert.convertToResponseDto(any(Menu.class))).thenReturn(menuResponseDTO);

        webTestClient.post()
                .uri("/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(menuRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(MenuResponseDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response.getId(), menuResponseDTO.getId());
                    assertEquals(response.getName(), menuResponseDTO.getName());
                });
        Mockito.verify(menuService).addMenu(any(Menu.class));
    }

    @Test
    @DisplayName("Obtener Menu por ID")
    void getMenu() {
        Menu menu = new Menu("Menu 1");
        menu.setId(1L);

        MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
        menuResponseDTO.setId(1L);
        menuResponseDTO.setName("Menu 1");

        when(menuService.getMenu(1L)).thenReturn(Optional.of(menu));
        when(menuDtoConvert.convertToResponseDto(any(Menu.class))).thenReturn(menuResponseDTO);

        webTestClient.get()
                .uri("/menu/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(MenuResponseDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response.getId(), menuResponseDTO.getId());
                    assertEquals(response.getName(), menuResponseDTO.getName());
                });
        Mockito.verify(menuService).getMenu(1L);
    }

    @Test
    @DisplayName("Obtener Todos los Menus")
    void getMenus() {
        Menu menu1 = new Menu("Menu 1");
        menu1.setId(1L);

        Menu menu2 = new Menu("Menu 2");
        menu2.setId(2L);

        List<Menu> menus = List.of(menu1, menu2);

        MenuResponseDTO menuResponseDTO1 = new MenuResponseDTO();
        menuResponseDTO1.setId(1L);
        menuResponseDTO1.setName("Menu 1");

        MenuResponseDTO menuResponseDTO2 = new MenuResponseDTO();
        menuResponseDTO2.setId(2L);
        menuResponseDTO2.setName("Menu 2");

        when(menuService.getMenus()).thenReturn(menus);
        when(menuDtoConvert.convertToResponseDto(menu1)).thenReturn(menuResponseDTO1);
        when(menuDtoConvert.convertToResponseDto(menu2)).thenReturn(menuResponseDTO2);

        webTestClient.get()
                .uri("/menu")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(MenuResponseDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(2, response.size());
                    assertEquals(response.get(0).getId(), menuResponseDTO1.getId());
                    assertEquals(response.get(0).getName(), menuResponseDTO1.getName());
                    assertEquals(response.get(1).getId(), menuResponseDTO2.getId());
                    assertEquals(response.get(1).getName(), menuResponseDTO2.getName());
                });
        Mockito.verify(menuService).getMenus();
    }

    @Test
    @DisplayName("Actualizar Menu")
    void updateMenu() {
        MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
        menuRequestDTO.setName("Menu Actualizado");

        Menu menu = new Menu("Menu Actualizado");
        menu.setId(1L);

        when(menuService.updateMenu(any(Long.class), any(Menu.class))).thenReturn(menu);

        webTestClient.put()
                .uri("/menu/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(menuRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response, "Menu actualizado exitosamente");
                });
        Mockito.verify(menuService).updateMenu(any(Long.class), any(Menu.class));
    }

    @Test
    @DisplayName("Actualizar Menu - No Encontrado")
    void updateMenuNotFound() {
        MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
        menuRequestDTO.setName("Menu Actualizado");

        when(menuService.updateMenu(any(Long.class), any(Menu.class)))
                .thenThrow(new RuntimeException("Menu with id 1 could not be updated"));

        webTestClient.put()
                .uri("/menu/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(menuRequestDTO)
                .exchange()
                .expectStatus().isNotFound();
        Mockito.verify(menuService).updateMenu(any(Long.class), any(Menu.class));
    }

    @Test
    @DisplayName("Eliminar Menu")
    void deleteMenu() {
        Mockito.doNothing().when(menuService).deleteMenu(1L);

        webTestClient.delete()
                .uri("/menu/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(response, "Menu eliminado exitosamente");
                });
        Mockito.verify(menuService).deleteMenu(1L);
    }
}