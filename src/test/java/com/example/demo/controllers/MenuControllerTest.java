package com.example.demo.controllers;

import com.example.demo.DTO.menu.MenuRequestDTO;
import com.example.demo.DTO.menu.MenuResponseDTO;
import com.example.demo.services.MenuService;
import org.junit.jupiter.api.BeforeEach;
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
@WebFluxTest(MenuController.class)
class MenuControllerTest {
    @MockBean
    private MenuService menuService;

    @Autowired
    private WebTestClient webTestClient;

    private MenuRequestDTO menuRequestDTO;
    private MenuResponseDTO menuResponseDTO;

    @BeforeEach
    void setUp() {
        menuRequestDTO = new MenuRequestDTO("Pizza");

        menuResponseDTO = MenuResponseDTO.builder()
                .id(1L)
                .name("Pizza")
                .build();
    }

    @Test
    void addMenu() {
        when(menuService.createMenu(any(MenuRequestDTO.class))).thenReturn(menuResponseDTO);

        webTestClient.post()
                .uri("/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(menuRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MenuResponseDTO.class)
                .value(response->{
                    assertEquals(menuResponseDTO.getId(),response.getId());
                    assertEquals(menuResponseDTO.getName(),response.getName());
                    assertEquals(menuResponseDTO.getDishfoods(),response.getDishfoods());
                });
        verify(menuService).createMenu(any(MenuRequestDTO.class));
    }

    @Test
    void findMenuById() {
        when(menuService.getMenuById(anyLong())).thenReturn(menuResponseDTO);
        webTestClient.get()
                .uri("/menus/{id}",1L)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MenuResponseDTO.class)
                .value(response->{
                    assertEquals(menuResponseDTO.getId(),response.getId());
                    assertEquals(menuResponseDTO.getName(),response.getName());
                });
        verify(menuService).getMenuById(anyLong());

    }

    @Test
    void findAllMenu() {
        when(menuService.getAllMenus()).thenReturn(getMenuList());
        webTestClient.get()
                .uri("/menus")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(MenuResponseDTO.class)
                .hasSize(3)
                .value(response->{
                    assertEquals("Menu mexicano", response.get(0).getName());
                    assertEquals("Menu uruguay", response.get(1).getName());
                    assertEquals("menu venezuela", response.get(2).getName());
                });
        verify(menuService).getAllMenus();
    }



    @Test
    void updateMenu() {
        when(menuService.updateMenu(anyLong(),any(MenuRequestDTO.class))).thenReturn(menuResponseDTO);
        webTestClient.put()
                .uri("/menus/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(menuRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MenuResponseDTO.class)
                .value(response->{
                    assertEquals(menuResponseDTO.getId(),response.getId());
                    assertEquals(menuResponseDTO.getName(),response.getName());
                });
        verify(menuService).updateMenu(anyLong(),any(MenuRequestDTO.class));
    }

    @Test
    void removeMenu() {
        doNothing().when(menuService).deleteMenu(anyLong());
        webTestClient.delete()
                .uri("/menus/delete/{id}",1L)
                .exchange()
                .expectStatus().isNoContent();
        verify(menuService).deleteMenu(anyLong());
    }
    private List<MenuResponseDTO> getMenuList() {
        return List.of(
                MenuResponseDTO.builder()
                        .id(1L)
                        .name("Menu mexicano")
                        .build(),

                MenuResponseDTO.builder()
                        .id(2L)
                        .name("Menu uruguay")
                        .build(),

                MenuResponseDTO.builder()
                        .id(3L)
                        .name("menu venezuela")
                        .build()


        );
    }
}