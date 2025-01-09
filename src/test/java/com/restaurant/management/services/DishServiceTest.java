package com.restaurant.management.services;

import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Menu;
import com.restaurant.management.repositories.ClientRepository;
import com.restaurant.management.repositories.DishRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class DishServiceTest {
  private final DishRepository dishRepository;
  private final DishService dishService;

  DishServiceTest() {
    dishRepository = mock(DishRepository.class);
    this.dishService = new DishService(dishRepository);
  }

  @Test
  @DisplayName("Agregar plato")
  void addDish() {
    Menu menu = new Menu(1L, "nameMenu");
    Dish dish = new Dish(1L, "name", "description", 10f, menu);
    when(dishRepository.save(eq(dish))).thenReturn(dish);

    Dish actualDish = dishService.addDish(dish);

    assertEquals(dish.getId(), actualDish.getId());
    assertEquals(dish.getName(), actualDish.getName());
    assertEquals(dish.getDescription(), actualDish.getDescription());
    assertEquals(dish.getPrice(), actualDish.getPrice());
    assertEquals(dish.getMenu(), actualDish.getMenu());

    verify(dishRepository).save(dish);
  }

  @Test
  @DisplayName("Obtener plato por id")
  void getDishById() {
    Menu menu = new Menu(1L, "nameMenu");
    Dish dish = new Dish(1L, "name", "description", 10f, menu);
    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dish));

    Optional<Dish> retrievedDish = dishService.getDishById(1L);

    assertTrue(retrievedDish.isPresent());
    assertEquals(dish.getId(), retrievedDish.get().getId());

    verify(dishRepository).findById(1L);
  }

  @Test
  @DisplayName("Obtener lista de platos")
  void getDishes() {
    when(dishRepository.findAll()).thenReturn(getDishList());

    List<Dish> retrievedDishes = dishService.getDishes();

    assertEquals(getDishList().size(), retrievedDishes.size());
    assertEquals(getDishList().get(0).getId(), retrievedDishes.get(0).getId());
    assertEquals(getDishList().get(1).getId(), retrievedDishes.get(1).getId());
    assertEquals(getDishList().get(2).getId(), retrievedDishes.get(2).getId());

    verify(dishRepository).findAll();
  }

  @Test
  @DisplayName("Actualizar plato exitoso")
  void updateDish() {
    Menu menu = new Menu(1L, "nameMenu");
    Dish dish = new Dish(1L, "name", "description", 10f, menu);
    Dish updatedDish = new Dish("Updated Name", "updated@example.com", 20f, menu);
    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dish));
    when(dishRepository.save(eq(dish))).thenAnswer(invocation -> invocation.getArgument(0));

    Dish result = dishService.updateDish(1L, updatedDish);

    assertEquals("Updated Name", result.getName());
    assertEquals("updated@example.com", result.getDescription());
    assertEquals(20f, result.getPrice());
    assertEquals(menu, result.getMenu());

    verify(dishRepository).findById(anyLong());
    verify(dishRepository).save(dish);
  }

  @Test
  @DisplayName("Actualizar plato con error")
  void updateDishError() {
    Menu menu = new Menu();
    Dish dish = new Dish(1L, "name", "description", 10f, menu);
    menu.addDish(dish);
    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dish));
    when(dishRepository.save(eq(dish))).thenThrow(new RuntimeException("Error al guardar el plato"));

    Exception exception = assertThrows(RuntimeException.class, () -> dishService.updateDish(1L, dish));

    assertEquals("Error al guardar el plato", exception.getMessage());

    verify(dishRepository).findById(anyLong());
    verify(dishRepository).save(dish);
  }

  @Test
  @DisplayName("Eliminar plato exitoso")
  void deleteDish() {
    Menu menu = new Menu();
    Dish dish = new Dish(1L, "name", "description", 10f, menu);
    menu.addDish(dish);

    when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dish));
    doNothing().when(dishRepository).deleteById(anyLong());

    dishService.deleteDish(1L);

    assertFalse(menu.getDishes().contains(dish));

    verify(dishRepository).findById(anyLong());
    verify(dishRepository).deleteById(anyLong());
  }

  @Test
  @DisplayName("Eliminar plato con error")
  void deleteDishError() {
    when(dishRepository.findById(anyLong())).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> dishService.deleteDish(1L));

    assertEquals("Plato no encontrado", exception.getMessage());

    verify(dishRepository).findById(anyLong());
    verify(dishRepository, never()).deleteById(anyLong());
  }

  private List<Dish> getDishList() {
    return List.of(new Dish(1L, "A", "Ve", 10f, new Menu(1L, "nameMenu")),
      new Dish(2L, "B", "Ce", 20f, new Menu(1L, "nameMenu")),
      new Dish(3L, "C", "De", 30f, new Menu(1L, "nameMenu")));
  }
}