package com.example.demo.services;

import com.example.demo.DTO.converterDTO.DishfoodConverter;
import com.example.demo.DTO.dishfood.DishfoodRequestDTO;
import com.example.demo.DTO.dishfood.DishfoodResponseDTO;
import com.example.demo.models.Dishfood;
import com.example.demo.models.Menu;
import com.example.demo.repositories.DishfoodRepository;
import com.example.demo.repositories.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DishFoodServiceTest {
    @Mock
    private DishfoodRepository dishfoodRepository;
    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private DishFoodService dishFoodService;

    private DishfoodRequestDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dto = new DishfoodRequestDTO("Pasta", 12.5, true, 1L);

    }

    @Test
    void createDishFood() {
        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("Italian Menu");
        Dishfood dishfood = DishfoodConverter.toEntity(dto, menu);
        dishfood.setId(1L);
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(dishfoodRepository.save(any(Dishfood.class))).thenReturn(dishfood);
        DishfoodResponseDTO response = dishFoodService.createDishFood(dto);
        assertNotNull(response);
        assertEquals("Pasta", response.getName());
        assertEquals(12.5, response.getPrice());
        verify(menuRepository).findById(1L);
        verify(dishfoodRepository).save(any(Dishfood.class));

    }

    @Test
    void getAllDishfoods() {
        Menu menu = new Menu();
        menu.setName("menu prueba");

        Dishfood dishfood = Dishfood.builder()
                .id(1L)
                .name("tacos")
                .price(20.0)
                .isPopular(false)
                .menu(menu)
                .build();
        when(dishfoodRepository.findById(any())).thenReturn(Optional.of(dishfood));
        DishfoodResponseDTO dishfoodResponseDTO = dishFoodService.getDishfoodById(1L);
        assertNotNull(dishfoodResponseDTO);
        assertEquals("tacos", dishfoodResponseDTO.getName());
        assertEquals(20.0, dishfoodResponseDTO.getPrice());
        verify(dishfoodRepository).findById(any());

    }

    @Test
    void getDishfoodById() {
        Menu menu = new Menu();
        menu.setName("menu prueba");
        when(dishfoodRepository.findAll()).thenReturn(getDishfoodList(menu));
        List<DishfoodResponseDTO> response = dishFoodService.getAllDishfoods();
        assertNotNull(response);
        assertEquals("Pasta",response.get(0).getName());
        assertEquals("pizza",response.get(1).getName());
        assertEquals("hamburguesa",response.get(2).getName());
        assertEquals(false,response.get(2).getIsPopular());
        verify(dishfoodRepository).findAll();


    }

    @Test
    void updateDishfood() {
        Menu menu = new Menu();
        menu.setName("menu prueba");
        Dishfood dishfood = Dishfood.builder()
                .id(1L)
                .name("tacos")
                .price(20.0)
                .isPopular(false)
                .menu(menu)
                .build();
        when(dishfoodRepository.findById(1L)).thenReturn(Optional.of(dishfood));
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(dishfoodRepository.save(dishfood)).thenReturn(dishfood);
        DishfoodResponseDTO response = dishFoodService.updateDishfood(1L, dto);
        assertNotNull(response);
        assertEquals("Pasta", response.getName());
        assertEquals(12.5, response.getPrice());
        verify(dishfoodRepository).findById(1L);
        verify(menuRepository).findById(1L);
        verify(dishfoodRepository).save(dishfood);
    }

    @Test
    void deleteDishfood() {
        when(dishfoodRepository.existsById(1L)).thenReturn(true);

        dishFoodService.deleteDishfood(1L);

        verify(dishfoodRepository).existsById(1L);
        verify(dishfoodRepository).deleteById(1L);

    }
    @Test
    void deleteDishfoodError() {
        Long dishFoodId = 1L;
        when(dishfoodRepository.existsById(dishFoodId)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> dishFoodService.deleteDishfood(dishFoodId));
        assertEquals("Dishfood not found", exception.getMessage());

        verify(dishfoodRepository).existsById(dishFoodId);
        verify(dishfoodRepository, never()).deleteById(dishFoodId);

    }

    private List<Dishfood> getDishfoodList(Menu menu) {
        return List.of(
                Dishfood.builder().id(1L).name("Pasta").price(12.5).isPopular(false).menu(menu).build(),
                Dishfood.builder().id(2L).name("pizza").price(17.5).isPopular(true).menu(menu).build(),
                Dishfood.builder().id(3L).name("hamburguesa").price(19.5).isPopular(false).menu(menu).build()

        );

    }
}