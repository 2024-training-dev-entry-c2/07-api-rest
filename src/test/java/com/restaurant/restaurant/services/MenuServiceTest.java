package com.restaurant.restaurant.services;

import com.restaurant.restaurant.dtos.DishDTO;
import com.restaurant.restaurant.dtos.MenuDTO;
import com.restaurant.restaurant.models.DishModel;
import com.restaurant.restaurant.models.MenuModel;
import com.restaurant.restaurant.repositories.IDishRepository;
import com.restaurant.restaurant.repositories.IMenuRepository;
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

class MenuServiceTest {

  @Mock
  private IMenuRepository IMenuRepository;

  @Mock
  private IDishRepository IDishRepository;

  @InjectMocks
  private MenuService menuService;

  private MenuModel menuModel;
  private MenuDTO menuDTO;
  private DishModel dishModel;
  private DishDTO dishDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    dishModel = new DishModel();
    dishModel.setId(1L);
    dishModel.setName("Pasta");
    dishModel.setPrice(10.0);
    dishModel.setType(com.restaurant.restaurant.enums.DishType.COMUN);

    dishDTO = new DishDTO(1L, "Pasta", 10.0, com.restaurant.restaurant.enums.DishType.COMUN);

    menuModel = new MenuModel();
    menuModel.setId(1L);
    menuModel.setName("Lunch Menu");
    menuModel.setDescription("A variety of dishes for lunch.");
    menuModel.setDishes(List.of(dishModel));

    menuDTO = new MenuDTO(1L, "Lunch Menu", "A variety of dishes for lunch.", List.of(dishDTO));
  }

  @Test
  @DisplayName("Test findAll() - Should return all menus")
  void findAll() {
    when(IMenuRepository.findAll()).thenReturn(List.of(menuModel));

    List<MenuDTO> menus = menuService.findAll();

    assertNotNull(menus);
    assertEquals(1, menus.size());
    assertEquals(menuDTO.getName(), menus.get(0).getName());
  }

  @Test
  @DisplayName("Test findById() - Should return menu by id")
  void findById() {
    when(IMenuRepository.findById(anyLong())).thenReturn(Optional.of(menuModel));

    MenuDTO foundMenu = menuService.findById(1L);

    assertNotNull(foundMenu);
    assertEquals(menuDTO.getName(), foundMenu.getName());
  }

  @Test
  @DisplayName("Test findById() - Should throw RuntimeException if menu does not exist")
  void findByIdNotFound() {
    when(IMenuRepository.findById(anyLong())).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      menuService.findById(999L);
    });

    assertEquals("Menu not found with id 999", exception.getMessage());
  }

  @Test
  @DisplayName("Test createMenu() - Should create and return a menu")
  void createMenu() {
    when(IMenuRepository.save(any(MenuModel.class))).thenReturn(menuModel);
    when(IDishRepository.findById(anyLong())).thenReturn(Optional.of(dishModel));

    MenuDTO createdMenu = menuService.createMenu(menuDTO);

    assertNotNull(createdMenu);
    assertEquals(menuDTO.getName(), createdMenu.getName());
    assertEquals(menuDTO.getDescription(), createdMenu.getDescription());
  }

  @Test
  @DisplayName("Test createMenu() - Should throw RuntimeException if dish is not found")
  void createMenuDishNotFound() {
    MenuDTO invalidMenuDTO = new MenuDTO(1L, "Dinner Menu", "A variety of dishes for dinner", List.of(new DishDTO(999L, "Invalid Dish", 15.0, com.restaurant.restaurant.enums.DishType.COMUN)));
    when(IDishRepository.findById(anyLong())).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      menuService.createMenu(invalidMenuDTO);
    });

    assertEquals("Dish not found with id 999", exception.getMessage());
  }

  @Test
  @DisplayName("Test updateMenu() - Should update and return the menu")
  void updateMenu() {
    when(IMenuRepository.findById(anyLong())).thenReturn(Optional.of(menuModel));
    when(IMenuRepository.save(any(MenuModel.class))).thenReturn(menuModel);
    when(IDishRepository.findById(anyLong())).thenReturn(Optional.of(dishModel));

    MenuDTO updatedMenuDTO = new MenuDTO(1L, "Updated Lunch Menu", "A variety of updated dishes.", List.of(dishDTO));
    MenuDTO updatedMenu = menuService.updateMenu(1L, updatedMenuDTO);

    assertNotNull(updatedMenu);
    assertEquals(updatedMenuDTO.getName(), updatedMenu.getName());
    assertEquals(updatedMenuDTO.getDescription(), updatedMenu.getDescription());
  }

  @Test
  @DisplayName("Test updateMenu() - Should throw RuntimeException if menu does not exist")
  void updateMenuNotFound() {
    when(IMenuRepository.findById(anyLong())).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      menuService.updateMenu(999L, menuDTO);
    });

    assertEquals("Menu not found with id 999", exception.getMessage());
  }

  @Test
  @DisplayName("Test deleteMenu() - Should delete a menu successfully")
  void deleteMenu() {
    when(IMenuRepository.existsById(anyLong())).thenReturn(true);
    doNothing().when(IMenuRepository).deleteById(anyLong());

    menuService.deleteMenu(1L);

    verify(IMenuRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Test deleteMenu() - Should throw RuntimeException if menu does not exist")
  void deleteMenuNotFound() {
    when(IMenuRepository.existsById(anyLong())).thenReturn(false);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      menuService.deleteMenu(999L);
    });

    assertEquals("Menu not found with id 999", exception.getMessage());
  }
}
