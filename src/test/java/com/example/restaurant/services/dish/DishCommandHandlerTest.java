package com.example.restaurant.services.dish;

import com.example.restaurant.models.dto.dish.DishRequestDTO;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import com.example.restaurant.services.dish.commands.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class DishCommandHandlerTest {

    @Mock
    private CreateDishCommand createDishCommand;

    @Mock
    private UpdateDishCommand updateDishCommand;

    @Mock
    private DeleteDishCommand deleteDishCommand;

    @Mock
    private GetDishByIdCommand getDishByIdCommand;

    @Mock
    private ListDishesCommand listDishesCommand;

    @InjectMocks
    private DishCommandHandler dishCommandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test de creación de un plato")
    void createDish() {
        DishRequestDTO requestDTO = new DishRequestDTO();
        DishResponseDTO responseDTO = new DishResponseDTO();

        when(createDishCommand.execute(any(DishRequestDTO.class))).thenReturn(responseDTO);

        DishResponseDTO result = dishCommandHandler.createDish(requestDTO);

        assertEquals(responseDTO, result);
        verify(createDishCommand).execute(requestDTO);
    }

    @Test
    @DisplayName("Test de actualización de un plato")
    void updateDish() {
        Long dishId = 1L;
        DishRequestDTO requestDTO = new DishRequestDTO();
        DishResponseDTO responseDTO = new DishResponseDTO();

        when(updateDishCommand.execute(anyLong(), any(DishRequestDTO.class))).thenReturn(responseDTO);

        DishResponseDTO result = dishCommandHandler.updateDish(dishId, requestDTO);

        assertEquals(responseDTO, result);
        verify(updateDishCommand).execute(dishId, requestDTO);
    }

    @Test
    @DisplayName("Test de eliminación de un plato")
    void deleteDish() {
        Long dishId = 1L;

        doNothing().when(deleteDishCommand).execute(anyLong());

        dishCommandHandler.deleteDish(dishId);

        verify(deleteDishCommand).execute(dishId);
    }

    @Test
    @DisplayName("Test de obtención de un plato por ID")
    void getDishById() {
        Long dishId = 1L;
        DishResponseDTO responseDTO = new DishResponseDTO();

        when(getDishByIdCommand.execute(anyLong())).thenReturn(responseDTO);

        DishResponseDTO result = dishCommandHandler.getDishById(dishId);

        assertEquals(responseDTO, result);
        verify(getDishByIdCommand).execute(dishId);
    }

    @Test
    @DisplayName("Test de listado de platos")
    void listDishes() {
        List<DishResponseDTO> responseDTOList = List.of(new DishResponseDTO());

        when(listDishesCommand.execute()).thenReturn(responseDTOList);

        List<DishResponseDTO> result = dishCommandHandler.listDishes();

        assertEquals(responseDTOList, result);
        verify(listDishesCommand).execute();
    }
}