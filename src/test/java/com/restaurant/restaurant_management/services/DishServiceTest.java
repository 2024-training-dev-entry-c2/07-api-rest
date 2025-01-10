package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.models.Menu;
import com.restaurant.restaurant_management.repositories.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DishServiceTest {
  private DishRepository dishRepository;
  private DishService dishService;

  private Menu menu;
  private Dish newDish;
  private Dish dish;
  private Dish dish2;
  private List<Dish> dishes;
  private Dish updatedDish;
  private Dish savedDish;

  @BeforeEach
  void setUp() {
    dishRepository = mock(DishRepository.class);
    dishService = new DishService(dishRepository);

    menu = new Menu(1, "Menu 1", "Descripcion del menu 1", true);
    newDish = new Dish(null, "Pollo", "Ocho presas", 45000, true, true, menu);
    dish = new Dish(1, "Pollo", "Ocho presas", 45000, true, true, menu);
    dish2 = new Dish(2, "Nuggets", "Con papas fritas y salsa de manzana", 21900, true, true, menu);
    dishes = List.of(dish, dish2);
    updatedDish = new Dish(null, "Pollo apanado", "Ocho presas con arepas", 48000, true, true, menu);
    savedDish = new Dish(1, "Pollo apanado", "Ocho presas con arepas", 48000, true, true, menu);
  }

  @Test
  @DisplayName("Save Dish")
  void saveDish() {
    when(dishRepository.save(newDish)).thenReturn(dish);
    Dish result = dishService.saveDish(newDish);
    assertNotNull(result);
    assertEquals(dish.getId(), result.getId());
    Mockito.verify(dishRepository, Mockito.times(1)).save(newDish);
  }

  @Test
  @DisplayName("Get Dish")
  void getDish() {
    when(dishRepository.findById(1)).thenReturn(Optional.of(dish));
    Optional<Dish> result = dishService.getDish(1);
    assertTrue(result.isPresent());
    assertEquals(dish.getId(), result.get().getId());
    Mockito.verify(dishRepository, Mockito.times(1)).findById(1);
  }

  @Test
  @DisplayName("List Dishes")
  void listDishes() {
    when(dishRepository.findAll()).thenReturn(dishes);
    List<Dish> result = dishService.listDishes();
    assertNotNull(result);
    assertEquals(dishes.size(), result.size());
    Mockito.verify(dishRepository, Mockito.times(1)).findAll();
  }

  @Test
  @DisplayName("List Dishes By Menu Id And Active")
  void listDishesByMenuIdAndActive() {
    when(dishRepository.findByMenuIdAndActiveTrue(1)).thenReturn(dishes);
    List<Dish> result = dishService.listDishesByMenuIdAndActive(1);
    assertNotNull(result);
    assertEquals(dishes.size(), result.size());
    Mockito.verify(dishRepository, Mockito.times(1)).findByMenuIdAndActiveTrue(1);
  }

  @Test
  @DisplayName("Update Dish")
  void updateDish() {
    when(dishRepository.findById(1)).thenReturn(Optional.of(dish));
    when(dishRepository.save(any(Dish.class))).thenReturn(savedDish);

    Dish result = dishService.updateDish(1, updatedDish);

    assertEquals(savedDish.getId(), result.getId());
    assertEquals("Pollo apanado", result.getDishName());
    assertEquals("Ocho presas con arepas", result.getDescription());
    assertEquals(48000, result.getBasePrice());

    Mockito.verify(dishRepository, Mockito.times(1)).findById(1);
    Mockito.verify(dishRepository, Mockito.times(1)).save(any(Dish.class));
  }

  @Test
  @DisplayName("Update Dish - Not Found")
  void updateDish_notFound() {
    when(dishRepository.findById(1)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> dishService.updateDish(1, new Dish()));
    assertEquals("Plato con el id 1 no pudo ser actualizado", exception.getMessage());
    Mockito.verify(dishRepository, Mockito.times(1)).findById(1);
    Mockito.verify(dishRepository, Mockito.never()).save(any(Dish.class));
  }

}