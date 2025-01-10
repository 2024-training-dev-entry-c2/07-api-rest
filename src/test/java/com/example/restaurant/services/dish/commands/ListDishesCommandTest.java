package com.example.restaurant.services.dish.commands;

import com.example.restaurant.mappers.DishMapper;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import com.example.restaurant.repositories.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListDishesCommandTest {

    private final DishRepository dishRepository = mock(DishRepository.class);
    private final DishMapper dishMapper = mock(DishMapper.class);

    @InjectMocks
    private ListDishesCommand listDishesCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test de servicio de listar todos los platos")
    void execute() {

        when(dishRepository.findAll()).thenReturn(getDishList());
        when(dishMapper.toDTO(getDishList().get(0))).thenReturn(new DishResponseDTO(1L, "Dish 1", 10.0f));
        when(dishMapper.toDTO(getDishList().get(1))).thenReturn(new DishResponseDTO(2L, "Dish 2", 20.0f));

       List<DishResponseDTO> dishesResponseDTO = listDishesCommand.execute();

        assertNotNull(dishesResponseDTO);
        assertEquals(2, dishesResponseDTO.size());
        assertEquals(1L, dishesResponseDTO.get(0).getCustomerId());
        assertEquals("Dish 1", dishesResponseDTO.get(0).getName());
        assertEquals(10f, dishesResponseDTO.get(0).getPrice());
        assertEquals(2L, dishesResponseDTO.get(1).getCustomerId());
        assertEquals("Dish 2", dishesResponseDTO.get(1).getName());
        assertEquals(20f, dishesResponseDTO.get(1).getPrice());
    }

    public List<Dish> getDishList() {
        Dish dish1 = new Dish(
                1L,
                "Dish 1",
                10.0f
        );

        Dish dish2 = new Dish(
                2L,
                "Dish 2",
                20.0f
        );

        return List.of(dish1, dish2);
    }
}