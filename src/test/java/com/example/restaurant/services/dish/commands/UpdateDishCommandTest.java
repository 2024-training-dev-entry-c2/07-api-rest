package com.example.restaurant.services.dish.commands;

import com.example.restaurant.mappers.DishMapper;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.dto.dish.DishRequestDTO;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import com.example.restaurant.repositories.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class UpdateDishCommandTest {

    @Mock
    private DishRepository dishRepository;

    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private UpdateDishCommand updateDishCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test de servicio de actualización de un plato")
    void execute() {
        // Arrange
        Long dishId = 1L;
        DishRequestDTO dishRequestDTO = new DishRequestDTO();
        dishRequestDTO.setName("Updated Dish");
        dishRequestDTO.setPrice(15.0f);

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setName("Old Dish");
        existingDish.setPrice(10.0f);

        Dish updatedDish = new Dish();
        updatedDish.setId(dishId);
        updatedDish.setName("Updated Dish");
        updatedDish.setPrice(15.0f);

        DishResponseDTO dishResponseDTO = new DishResponseDTO();
        dishResponseDTO.setId(dishId);
        dishResponseDTO.setName("Updated Dish");
        dishResponseDTO.setPrice(15.0f);

        when(dishRepository.findById(anyLong())).thenReturn(Optional.of(existingDish));
        when(dishMapper.toEntity(any(DishRequestDTO.class))).thenReturn(updatedDish);
        when(dishRepository.save(any(Dish.class))).thenReturn(updatedDish);
        when(dishMapper.toDTO(any(Dish.class))).thenReturn(dishResponseDTO);

        // Act
        DishResponseDTO result = updateDishCommand.execute(dishId, dishRequestDTO);

        // Assert
        assertEquals(dishResponseDTO, result);
    }

    @Test
    @DisplayName("Test de servicio de actualización de un plato con ID no encontrado")
    void executeDishNotFound() {
        // Arrange
        Long dishId = 1L;
        DishRequestDTO dishRequestDTO = new DishRequestDTO();
        dishRequestDTO.setName("Updated Dish");
        dishRequestDTO.setPrice(15.0f);

        when(dishRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> updateDishCommand.execute(dishId, dishRequestDTO));
    }
}