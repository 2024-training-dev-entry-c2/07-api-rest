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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateDishCommandTest {

    private final DishRepository dishRepository = mock(DishRepository.class);
    private final DishMapper dishMapper = mock(DishMapper.class);

    @InjectMocks
    private CreateDishCommand createDishCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test de servicio de creación de plato")
    void execute() {
        DishRequestDTO dishRequestDTO = new DishRequestDTO();
        dishRequestDTO.setName("Test Dish");
        dishRequestDTO.setPrice(10.0f);

        Dish dish = new Dish();
        dish.setName("Test Dish");
        dish.setPrice(10.0f);

        Dish savedDish = new Dish();
        savedDish.setDishId(1L);
        savedDish.setName("Test Dish");
        savedDish.setPrice(10.0f);

        DishResponseDTO dishResponseDTO = new DishResponseDTO();
        dishResponseDTO.setDishId(1L);
        dishResponseDTO.setName("Test Dish");
        dishResponseDTO.setPrice(10.0f);

        when(dishMapper.toEntity(any(DishRequestDTO.class))).thenReturn(dish);
        when(dishRepository.save(any(Dish.class))).thenReturn(savedDish);
        when(dishMapper.toDTO(any(Dish.class))).thenReturn(dishResponseDTO);

        DishResponseDTO result = createDishCommand.execute(dishRequestDTO);

        assertEquals(dishResponseDTO, result);

        Mockito.verify(dishMapper).toEntity(dishRequestDTO);
        Mockito.verify(dishRepository).save(dish);
    }
}