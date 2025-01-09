package com.example.restaurant.mappers;

import com.example.restaurant.models.Menu;
import com.example.restaurant.models.dto.menu.MenuRequestDTO;
import com.example.restaurant.models.dto.menu.MenuResponseDTO;
import com.example.restaurant.repositories.DishRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MenuMapperTest {

  private final DishRepository dishRepository = mock(DishRepository.class);
  private final MenuMapper menuMapper = new MenuMapper(dishRepository, new DishMapper());

  @Test
  @DisplayName("Convertir de RequestDTO a Entidad")
  void toEntity() {
    MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
    menuRequestDTO.setName("Menú del Día");
    menuRequestDTO.setDescription("Un menú delicioso con varios platos");

    Menu menuEntity = menuMapper.toEntity(menuRequestDTO);

    assertEquals("Menú del Día", menuEntity.getName());
    assertEquals("Un menú delicioso con varios platos", menuEntity.getDescription());
  }

  @Test
  @DisplayName("Convertir de Entidad a ResponseDTO")
  void toDTO() {
    Menu menuEntity = new Menu();
    menuEntity.setName("Menú del Día");
    menuEntity.setDescription("Un menú delicioso con varios platos");
    menuEntity.setDishes(new ArrayList<>()); // Inicializar la lista de platos

    MenuResponseDTO menuResponseDTO = menuMapper.toDTO(menuEntity);

    assertEquals("Menú del Día", menuResponseDTO.getName());
    assertEquals("Un menú delicioso con varios platos", menuResponseDTO.getDescription());
  }
}