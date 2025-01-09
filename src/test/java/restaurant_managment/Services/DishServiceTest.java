package restaurant_managment.Services;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Repositories.DishRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class DishServiceTest {

  private final DishRepository dishRepository = mock(DishRepository.class);
  private final EntityManager entityManager = mock(EntityManager.class);
  private final DishService dishService = new DishService(dishRepository, entityManager);

  @Test
  @DisplayName("Get all dishes")
  void getAllDishes() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, true, false, "Dish 2", 20.0, "Description 2");
    List<DishModel> dishes = List.of(dish1, dish2);

    when(dishRepository.findAll()).thenReturn(dishes);

    List<DishModel> result = dishService.getAllDishes();
    assertEquals(2, result.size());
    assertEquals(dishes, result);

    verify(dishRepository).findAll();
  }

  @Test
  @DisplayName("Get dish by ID")
  void getDishById() {
    DishModel dish = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");

    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dish));

    Optional<DishModel> result = dishService.getDishById(1L);
    assertEquals(Optional.of(dish), result);

    verify(dishRepository).findById(anyLong());
  }

  @Test
  @DisplayName("Save dish")
  void saveDish() {
    DishModel dish = mock(DishModel.class);

    when(dishRepository.save(any(DishModel.class))).thenReturn(dish);
    doNothing().when(dish).updatePopularity(any(EntityManager.class));

    DishModel result = dishService.saveDish(dish);
    assertEquals(dish, result);

    verify(dishRepository).save(any(DishModel.class));
    verify(dish).updatePopularity(any(EntityManager.class));
  }

  @Test
  @DisplayName("Update dish")
  void updateDish() {
    DishModel dish = mock(DishModel.class);
    DishModel updatedDish = mock(DishModel.class);

    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dish));
    when(dishRepository.save(any(DishModel.class))).thenReturn(updatedDish);
    doNothing().when(updatedDish).updatePopularity(any(EntityManager.class));

    DishModel result = dishService.updateDish(1L, updatedDish);
    assertEquals(updatedDish, result);

    verify(dishRepository).findById(anyLong());
    verify(dishRepository).save(any(DishModel.class));
    verify(updatedDish).updatePopularity(any(EntityManager.class));
  }

  @Test
  @DisplayName("Update dish - not found")
  void updateDishNotFound() {
    DishModel updatedDish = new DishModel(1L, true, false, "Updated Dish", 20.0, "Updated Description");

    when(dishRepository.findById(anyLong())).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      dishService.updateDish(1L, updatedDish);
    });

    assertEquals("Dish not found", exception.getMessage());

    verify(dishRepository).findById(anyLong());
    verify(dishRepository, never()).save(any(DishModel.class));
  }

  @Test
  @DisplayName("Delete dish")
  void deleteDish() {
    doNothing().when(dishRepository).deleteById(anyLong());

    dishService.deleteDish(1L);

    verify(dishRepository).deleteById(anyLong());
  }
}