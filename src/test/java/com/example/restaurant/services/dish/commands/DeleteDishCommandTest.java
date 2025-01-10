package com.example.restaurant.services.dish.commands;

import com.example.restaurant.repositories.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteDishCommandTest {

    private final DishRepository dishRepository = mock(DishRepository.class);

    @InjectMocks
    private DeleteDishCommand deleteDishCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test de servicio de eliminación de un plato")
    void execute() {
        Long dishId = 1L;

        when(dishRepository.existsById(dishId)).thenReturn(true);

        deleteDishCommand.execute(dishId);

        verify(dishRepository).deleteById(dishId);
    }

    @Test
    @DisplayName("Test de servicio de eliminación de un plato con ID no encontrado")
    void executeDishNotFound() {
        Long dishId = 1L;

        when(dishRepository.existsById(dishId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> deleteDishCommand.execute(dishId));

        verify(dishRepository, never()).deleteById(dishId);
    }
}