package com.api_restaurant.services;

import com.api_restaurant.models.Dish;
import com.api_restaurant.models.Menu;
import com.api_restaurant.repositories.DishRepository;
import com.api_restaurant.repositories.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DishServiceTest {

    private DishService dishService;
    private DishRepository dishRepository;
    private MenuRepository menuRepository;

    @BeforeEach
    void setUp() {
        dishRepository = mock(DishRepository.class);
        menuRepository = mock(MenuRepository.class);
        dishService = new DishService(dishRepository, menuRepository);
    }

    @Test
    @DisplayName("Add Dish - Success")
    void addDishSuccess() {
        Dish dish = new Dish("Dish 1", "Description 1", 10.0, null);
        Menu menu = new Menu();
        menu.setId(1L);

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(dishRepository.save(any(Dish.class))).thenReturn(dish);

        Dish result = dishService.addDish(dish, 1L);

        assertEquals(dish.getName(), result.getName());
        assertEquals(dish.getDescription(), result.getDescription());
        assertEquals(dish.getPrice(), result.getPrice());
        assertEquals(menu, result.getMenu());

        Mockito.verify(menuRepository).findById(1L);
        Mockito.verify(dishRepository).save(any(Dish.class));
    }

    @Test
    @DisplayName("Add Dish - Menu Not Found")
    void addDishMenuNotFound() {
        Dish dish = new Dish("Dish 1", "Description 1", 10.0, null);

        when(menuRepository.findById(1L)).thenReturn(Optional.empty());
        when(dishRepository.save(any(Dish.class))).thenReturn(dish);

        Dish result = dishService.addDish(dish, 1L);

        assertEquals(dish.getName(), result.getName());
        assertEquals(dish.getDescription(), result.getDescription());
        assertEquals(dish.getPrice(), result.getPrice());
        assertNull(result.getMenu());

        Mockito.verify(menuRepository).findById(1L);
        Mockito.verify(dishRepository).save(any(Dish.class));
    }

    @Test
    @DisplayName("Add Dish - No Menu")
    void addDishNoMenu() {
        Dish dish = new Dish("Dish 1", "Description 1", 10.0, null);

        when(dishRepository.save(any(Dish.class))).thenReturn(dish);

        Dish result = dishService.addDish(dish, null);

        assertEquals(dish.getName(), result.getName());
        assertEquals(dish.getDescription(), result.getDescription());
        assertEquals(dish.getPrice(), result.getPrice());
        assertNull(result.getMenu());

        Mockito.verify(dishRepository).save(any(Dish.class));
    }

    @Test
    @DisplayName("Get Dish - Found")
    void getDishFound() {
        Dish dish = new Dish("Dish 1", "Description 1", 10.0, null);
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));

        Optional<Dish> result = dishService.getDish(1L);

        assertTrue(result.isPresent());
        assertEquals(dish.getName(), result.get().getName());
        assertEquals(dish.getDescription(), result.get().getDescription());
        assertEquals(dish.getPrice(), result.get().getPrice());

        Mockito.verify(dishRepository).findById(1L);
    }

    @Test
    @DisplayName("Get Dish - Not Found")
    void getDishNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Dish> result = dishService.getDish(1L);

        assertTrue(result.isEmpty());

        Mockito.verify(dishRepository).findById(1L);
    }

    @Test
    @DisplayName("Get Dishes - Found")
    void getDishesFound() {
        Dish dish1 = new Dish("Dish 1", "Description 1", 10.0, null);
        Dish dish2 = new Dish("Dish 2", "Description 2", 15.0, null);
        List<Dish> dishes = List.of(dish1, dish2);

        when(dishRepository.findAll()).thenReturn(dishes);

        List<Dish> result = dishService.getDishes();

        assertEquals(2, result.size());
        assertEquals(dish1.getName(), result.get(0).getName());
        assertEquals(dish2.getName(), result.get(1).getName());

        Mockito.verify(dishRepository).findAll();
    }

    @Test
    @DisplayName("Get Dishes - Not Found")
    void getDishesNotFound() {
        when(dishRepository.findAll()).thenReturn(List.of());

        List<Dish> result = dishService.getDishes();

        assertTrue(result.isEmpty());

        Mockito.verify(dishRepository).findAll();
    }

    @Test
    @DisplayName("Update Dish - Success")
    void updateDishSuccess() {
        Dish existingDish = new Dish("Dish 1", "Description 1", 10.0, null);
        when(dishRepository.findById(1L)).thenReturn(Optional.of(existingDish));
        when(dishRepository.save(any(Dish.class))).thenReturn(existingDish);

        Dish updatedDish = new Dish("Dish Updated", "Description Updated", 20.0, null);
        Dish result = dishService.updateDish(1L, updatedDish);

        assertEquals(updatedDish.getName(), result.getName());
        assertEquals(updatedDish.getDescription(), result.getDescription());
        assertEquals(updatedDish.getPrice(), result.getPrice());

        Mockito.verify(dishRepository).findById(1L);
        Mockito.verify(dishRepository).save(any(Dish.class));
    }

    @Test
    @DisplayName("Update Dish - Not Found")
    void updateDishNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        Dish updatedDish = new Dish("Dish Updated", "Description Updated", 20.0, null);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dishService.updateDish(1L, updatedDish);
        });

        assertEquals("Dish with id 1 could not be updated", exception.getMessage());

        Mockito.verify(dishRepository).findById(1L);
        Mockito.verify(dishRepository, never()).save(any(Dish.class));
    }

    @Test
    @DisplayName("Update Dish - With Menu")
    void updateDishWithMenu() {
        Dish existingDish = new Dish("Dish 1", "Description 1", 10.0, null);
        Menu menu = new Menu();
        menu.setId(1L);
        Dish updatedDish = new Dish("Dish Updated", "Description Updated", 20.0, menu);

        when(dishRepository.findById(1L)).thenReturn(Optional.of(existingDish));
        when(dishRepository.save(any(Dish.class))).thenReturn(existingDish);

        Dish result = dishService.updateDish(1L, updatedDish);

        assertEquals(updatedDish.getName(), result.getName());
        assertEquals(updatedDish.getDescription(), result.getDescription());
        assertEquals(updatedDish.getPrice(), result.getPrice());
        assertEquals(menu, result.getMenu());

        Mockito.verify(dishRepository).findById(1L);
        Mockito.verify(dishRepository).save(any(Dish.class));
    }

    @Test
    @DisplayName("Update Dish - Special Dish")
    void updateDishSpecialDish() {
        Dish existingDish = new Dish("Dish 1", "Description 1", 10.0, null);
        existingDish.setSpecialDish(false);
        Dish updatedDish = new Dish("Dish Updated", "Description Updated", 20.0, null);
        updatedDish.setSpecialDish(true);

        when(dishRepository.findById(1L)).thenReturn(Optional.of(existingDish));
        when(dishRepository.save(any(Dish.class))).thenReturn(existingDish);

        Dish result = dishService.updateDish(1L, updatedDish);

        assertEquals(updatedDish.getName(), result.getName());
        assertEquals(updatedDish.getDescription(), result.getDescription());
        assertEquals(updatedDish.getPrice(), result.getPrice());
        assertTrue(result.getSpecialDish());

        Mockito.verify(dishRepository).findById(1L);
        Mockito.verify(dishRepository).save(any(Dish.class));
    }

    @Test
    @DisplayName("Delete Dish - Success")
    void deleteDishSuccess() {
        Dish dish = new Dish("Dish 1", "Description 1", 10.0, null);
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        doNothing().when(dishRepository).deleteById(1L);

        dishService.deleteDish(1L);

        Mockito.verify(dishRepository).findById(1L);
        Mockito.verify(dishRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Delete Dish - Not Found")
    void deleteDishNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dishService.deleteDish(1L);
        });

        assertEquals("Dish with id 1 not found", exception.getMessage());

        Mockito.verify(dishRepository).findById(1L);
        Mockito.verify(dishRepository, never()).deleteById(1L);
    }


    @Test
    @DisplayName("Delete Dish - With Observers")
    void deleteDishWithObservers() {
        Dish dish = new Dish("Dish 1", "Description 1", 10.0, null);
        Menu menu = new Menu();
        menu.setId(1L);
        dish.setMenu(menu);
        dish.addObserver(menu);

        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        doNothing().when(dishRepository).deleteById(1L);

        dishService.deleteDish(1L);

        Mockito.verify(dishRepository).findById(1L);
        Mockito.verify(dishRepository).deleteById(1L);
    }
}