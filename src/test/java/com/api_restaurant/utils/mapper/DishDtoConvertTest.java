package com.api_restaurant.utils.mapper;

import com.api_restaurant.dto.dish.DishRequestDTO;
import com.api_restaurant.dto.dish.DishResponseDTO;
import com.api_restaurant.models.Dish;
import com.api_restaurant.models.Menu;
import com.api_restaurant.repositories.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishDtoConvertTest {

    @Test
    @DisplayName("Convert to Response DTO - Success")
    void convertToResponseDto() {
        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("Menu 1");

        Dish dish = new Dish();
        dish.setId(1L);
        dish.setName("Dish 1");
        dish.setDescription("Description 1");
        dish.setPrice(10.0);
        dish.setSpecialDish(true);
        dish.setMenu(menu);

        DishResponseDTO responseDTO = DishDtoConvert.convertToResponseDto(dish);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Dish 1", responseDTO.getName());
        assertEquals("Description 1", responseDTO.getDescription());
        assertEquals(10.0, responseDTO.getPrice());
        assertTrue(responseDTO.getSpecialDish());
        assertNotNull(responseDTO.getMenu());
        assertEquals(1L, responseDTO.getMenu().getId());
        assertEquals("Menu 1", responseDTO.getMenu().getName());
    }

    @Test
    @DisplayName("Convert to Response DTO - No Menu")
    void convertToResponseDtoNoMenu() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setName("Dish 1");
        dish.setDescription("Description 1");
        dish.setPrice(10.0);
        dish.setSpecialDish(true);

        DishResponseDTO responseDTO = DishDtoConvert.convertToResponseDto(dish);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Dish 1", responseDTO.getName());
        assertEquals("Description 1", responseDTO.getDescription());
        assertEquals(10.0, responseDTO.getPrice());
        assertTrue(responseDTO.getSpecialDish());
        assertNull(responseDTO.getMenu());
    }

    @Test
    @DisplayName("Convert to Entity - Success")
    void convertToEntity() {
        MenuRepository menuRepository = mock(MenuRepository.class);
        Menu menu = new Menu();
        menu.setId(1L);
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        DishRequestDTO requestDTO = new DishRequestDTO();
        requestDTO.setName("Dish 1");
        requestDTO.setDescription("Description 1");
        requestDTO.setPrice(10.0);
        requestDTO.setMenuId(1L);

        Dish dish = DishDtoConvert.convertToEntity(requestDTO, menuRepository);

        assertNotNull(dish);
        assertEquals("Dish 1", dish.getName());
        assertEquals("Description 1", dish.getDescription());
        assertEquals(10.0, dish.getPrice());
        assertEquals(menu, dish.getMenu());
    }

    @Test
    @DisplayName("Convert to Entity - No Menu")
    void convertToEntityNoMenu() {
        MenuRepository menuRepository = mock(MenuRepository.class);

        DishRequestDTO requestDTO = new DishRequestDTO();
        requestDTO.setName("Dish 1");
        requestDTO.setDescription("Description 1");
        requestDTO.setPrice(10.0);

        Dish dish = DishDtoConvert.convertToEntity(requestDTO, menuRepository);

        assertNotNull(dish);
        assertEquals("Dish 1", dish.getName());
        assertEquals("Description 1", dish.getDescription());
        assertEquals(10.0, dish.getPrice());
        assertNull(dish.getMenu());
    }
}