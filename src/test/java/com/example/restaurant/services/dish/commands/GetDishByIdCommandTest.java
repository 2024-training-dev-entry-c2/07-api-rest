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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetDishByIdCommandTest {

    private final DishRepository dishRepository = mock(DishRepository.class);
    private final DishMapper dishMapper = mock(DishMapper.class);

    @InjectMocks
    private GetDishByIdCommand getDishByIdCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test de servicio de obtención de un plato por ID")
    void execute() {
        Dish dish = new Dish(1L, "Pizza", 10000.0f);

        DishResponseDTO dishResponseDTO = new DishResponseDTO();
        dishResponseDTO.setDishId(1L);
        dishResponseDTO.setName("Pizza");
        dishResponseDTO.setPrice(10000.0f);

        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(dishMapper.toDTO(dish)).thenReturn(dishResponseDTO);

        DishResponseDTO result = getDishByIdCommand.execute(1L);

        assertEquals(dishResponseDTO, result);
        verify(dishRepository).findById(1L);
    }

    @Test
    @DisplayName("Test de servicio de obtención de un plato por ID no encontrado")
    void executeDishNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> getDishByIdCommand.execute(1L));
        verify(dishRepository).findById(1L);
    }
}