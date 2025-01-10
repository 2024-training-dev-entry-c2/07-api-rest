package com.restaurant.restaurant.services;

import com.restaurant.restaurant.dtos.DishDTO;
import com.restaurant.restaurant.enums.DishType;
import com.restaurant.restaurant.exceptions.ResourceNotFoundException;
import com.restaurant.restaurant.models.DishModel;
import com.restaurant.restaurant.repositories.DishRepository;
import com.restaurant.restaurant.utils.UtilCost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class DishServiceTest {

  @Mock
  private DishRepository dishRepository;

  @InjectMocks
  private DishService dishService;

  private DishModel dishModel;
  private DishDTO dishDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    dishModel = new DishModel();
    dishModel.setId(1L);
    dishModel.setName("Pasta");
    dishModel.setPrice(10.0);
    dishModel.setType(DishType.COMUN);

    dishDTO = new DishDTO(1L, "Pasta", 10.0, DishType.COMUN);
  }

  @Test
  @DisplayName("Test findAll() - Should return all dishes")
  void findAll() {
    when(dishRepository.findAll()).thenReturn(List.of(dishModel));

    List<DishDTO> dishDTOs = dishService.findAll();

    assertNotNull(dishDTOs);
    assertEquals(1, dishDTOs.size());
    assertEquals(dishDTO.getName(), dishDTOs.get(0).getName());
  }

  @Test
  @DisplayName("Test findById() - Should return dish by id")
  void findById() {
    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dishModel));

    DishDTO foundDish = dishService.findById(1L);

    assertNotNull(foundDish);
    assertEquals(dishDTO.getName(), foundDish.getName());
  }

  @Test
  @DisplayName("Test findById() - Should throw ResourceNotFoundException if dish does not exist")
  void findByIdNotFound() {
    when(dishRepository.findById(anyLong())).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      dishService.findById(999L);
    });

    assertEquals("Dish not found with id 999", exception.getMessage());
  }

  @Test
  @DisplayName("Test createDish() - Should create and return a dish")
  void createDish() {
    when(dishRepository.save(any(DishModel.class))).thenReturn(dishModel);

    DishDTO createdDish = dishService.createDish(dishDTO);

    assertNotNull(createdDish);
    assertEquals(dishDTO.getName(), createdDish.getName());
    assertEquals(dishDTO.getPrice(), createdDish.getPrice());
  }

  @Test
  @DisplayName("Test updateDish() - Should update and return the dish")
  void updateDish() {
    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dishModel));
    when(dishRepository.save(any(DishModel.class))).thenReturn(dishModel);

    dishDTO.setPrice(12.0);
    DishDTO updatedDish = dishService.updateDish(1L, dishDTO);

    assertNotNull(updatedDish);
    assertEquals(dishDTO.getPrice(), updatedDish.getPrice());
  }

  @Test
  @DisplayName("Test updateDish() - Should apply increment if dish is of type POPULAR")
  void updateDishWithPriceIncrement() {
    dishModel.setType(DishType.POPULAR);
    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dishModel));
    when(dishRepository.save(any(DishModel.class))).thenReturn(dishModel);

    dishDTO.setPrice(10.0);
    DishDTO updatedDish = dishService.updateDish(1L, dishDTO);

    assertNotNull(updatedDish);
    assertTrue(updatedDish.getPrice() > 10.0);
  }

  @Test
  @DisplayName("Test updateDish() - Should throw ResourceNotFoundException if dish does not exist")
  void updateDishNotFound() {
    when(dishRepository.findById(anyLong())).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      dishService.updateDish(999L, dishDTO);
    });

    assertEquals("Dish not found with id 999", exception.getMessage());
  }

  @Test
  @DisplayName("Test deleteDish() - Should delete a dish successfully")
  void deleteDish() {
    when(dishRepository.existsById(anyLong())).thenReturn(true);
    doNothing().when(dishRepository).deleteById(anyLong());

    dishService.deleteDish(1L);

    verify(dishRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Test deleteDish() - Should throw ResourceNotFoundException if dish does not exist")
  void deleteDishNotFound() {
    when(dishRepository.existsById(anyLong())).thenReturn(false);

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      dishService.deleteDish(999L);
    });

    assertEquals("Dish not found with id 999", exception.getMessage());
  }

  @Test
  @DisplayName("Test isPopular() - Should return true if dish is popular")
  void isPopular() {
    dishModel.setType(DishType.POPULAR);
    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dishModel));

    boolean isPopular = dishService.isPopular(1L);

    assertTrue(isPopular);
  }

  @Test
  @DisplayName("Test isPopular() - Should return false if dish is not popular")
  void isPopularNotPopular() {
    dishModel.setType(DishType.COMUN);
    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dishModel));

    boolean isPopular = dishService.isPopular(1L);

    assertFalse(isPopular);
  }

  @Test
  @DisplayName("Test isPopular() - Should return false if dish does not exist")
  void isPopularDishNotFound() {
    when(dishRepository.findById(anyLong())).thenReturn(Optional.empty());

    boolean isPopular = dishService.isPopular(999L);

    assertFalse(isPopular);
  }
}
