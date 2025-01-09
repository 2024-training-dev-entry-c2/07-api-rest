package com.api_restaurant.utils.mapper;

import com.api_restaurant.dto.menu.MenuRequestDTO;
import com.api_restaurant.dto.menu.MenuResponseDTO;
import com.api_restaurant.models.Dish;
import com.api_restaurant.models.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuDtoConvertTest {

    private final MenuDtoConvert menuDtoConvert = new MenuDtoConvert();

    @Test
    @DisplayName("Convert to Response DTO - Success")
    void convertToResponseDto() {
        Dish dish1 = new Dish();
        dish1.setId(1L);
        dish1.setName("Dish 1");
        dish1.setDescription("Description 1");
        dish1.setPrice(10.0);

        Dish dish2 = new Dish();
        dish2.setId(2L);
        dish2.setName("Dish 2");
        dish2.setDescription("Description 2");
        dish2.setPrice(15.0);

        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("Menu 1");
        menu.setDishes(List.of(dish1, dish2));

        MenuResponseDTO responseDTO = menuDtoConvert.convertToResponseDto(menu);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Menu 1", responseDTO.getName());
        assertNotNull(responseDTO.getDishes());
        assertEquals(2, responseDTO.getDishes().size());
    }

    @Test
    @DisplayName("Convert to Menu Entity - Success")
    void convertToMenuEntity() {
        MenuRequestDTO requestDTO = new MenuRequestDTO();
        requestDTO.setName("Menu 1");

        Menu menu = menuDtoConvert.convertToMenuEntity(requestDTO);

        assertNotNull(menu);
        assertEquals("Menu 1", menu.getName());
    }
}