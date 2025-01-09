package restaurant_managment.Services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.MenuModel;
import restaurant_managment.Repositories.DishRepository;
import restaurant_managment.Repositories.MenuRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class MenuServiceTest {

  private final MenuRepository menuRepository = mock(MenuRepository.class);
  private final DishRepository dishRepository = mock(DishRepository.class);
  private final MenuService menuService = new MenuService();

  public MenuServiceTest() {
    menuService.menuRepository = menuRepository;
    menuService.dishRepository = dishRepository;
  }

  @Test
  @DisplayName("Get all menus")
  void getAllMenus() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    MenuModel menu1 = new MenuModel(1L, "Menu 1", List.of(dish1));
    MenuModel menu2 = new MenuModel(2L, "Menu 2", List.of(dish1));
    List<MenuModel> menus = List.of(menu1, menu2);

    when(menuRepository.findAll()).thenReturn(menus);

    List<MenuModel> result = menuService.getAllMenus();
    assertEquals(2, result.size());
    assertEquals(menus, result);

    verify(menuRepository).findAll();
  }

  @Test
  @DisplayName("Get menu by ID")
  void getMenuById() {
    DishModel dish = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    MenuModel menu = new MenuModel(1L, "Menu 1", List.of(dish));

    when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menu));

    Optional<MenuModel> result = menuService.getMenuById(1L);
    assertEquals(Optional.of(menu), result);

    verify(menuRepository).findById(anyLong());
  }

  @Test
  @DisplayName("Create menu")
  void createMenu() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<Long> dishIds = List.of(dish1.getId(), dish2.getId());
    List<DishModel> dishes = List.of(dish1, dish2);
    MenuModel menu = new MenuModel(1L, "Menu 1", dishes);

    when(dishRepository.findAllById(anyList())).thenReturn(dishes);
    when(menuRepository.save(any(MenuModel.class))).thenReturn(menu);

    MenuModel result = menuService.createMenu(menu, dishIds);
    assertEquals(menu, result);

    verify(dishRepository).findAllById(anyList());
    verify(menuRepository).save(any(MenuModel.class));
  }

  @Test
  @DisplayName("Create menu - one or more dishes not found")
  void createMenuDishesNotFound() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    List<Long> dishIds = List.of(dish1.getId(), 2L);  // 2L does not exist
    List<DishModel> dishes = List.of(dish1);
    MenuModel menu = new MenuModel(1L, "Menu 1", dishes);

    when(dishRepository.findAllById(anyList())).thenReturn(dishes);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      menuService.createMenu(menu, dishIds);
    });

    assertEquals("One or more dishes not found", exception.getMessage());

    verify(dishRepository).findAllById(anyList());
    verify(menuRepository, never()).save(any(MenuModel.class));
  }

  @Test
  @DisplayName("Update menu")
  void updateMenu() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    DishModel dish2 = new DishModel(2L, false, true, "Dish 2", 20.0, "Description 2");
    List<Long> dishIds = List.of(dish1.getId(), dish2.getId());
    List<DishModel> dishes = List.of(dish1, dish2);
    MenuModel menu = new MenuModel(1L, "Menu 1", List.of(dish1));
    MenuModel updatedMenu = new MenuModel(1L, "Updated Menu 1", dishes);

    when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menu));
    when(dishRepository.findAllById(anyList())).thenReturn(dishes);
    when(menuRepository.save(any(MenuModel.class))).thenReturn(updatedMenu);

    MenuModel result = menuService.updateMenu(1L, updatedMenu, dishIds);
    assertEquals(updatedMenu, result);

    verify(menuRepository).findById(anyLong());
    verify(dishRepository).findAllById(anyList());
    verify(menuRepository).save(any(MenuModel.class));
  }

  @Test
  @DisplayName("Update menu - one or more dishes not found")
  void updateMenuDishesNotFound() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    List<Long> dishIds = List.of(dish1.getId(), 2L);  // 2L does not exist
    List<DishModel> dishes = List.of(dish1);
    MenuModel menu = new MenuModel(1L, "Menu 1", List.of(dish1));
    MenuModel updatedMenu = new MenuModel(1L, "Updated Menu 1", List.of(dish1));

    when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menu));
    when(dishRepository.findAllById(anyList())).thenReturn(dishes);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      menuService.updateMenu(1L, updatedMenu, dishIds);
    });

    assertEquals("One or more dishes not found", exception.getMessage());

    verify(menuRepository).findById(anyLong());
    verify(dishRepository).findAllById(anyList());
    verify(menuRepository, never()).save(any(MenuModel.class));
  }

  @Test
  @DisplayName("Update menu - menu not found")
  void updateMenuNotFound() {
    DishModel dish1 = new DishModel(1L, false, true, "Dish 1", 10.0, "Description 1");
    List<Long> dishIds = List.of(dish1.getId());
    List<DishModel> dishes = List.of(dish1);
    MenuModel updatedMenu = new MenuModel(1L, "Updated Menu 1", dishes);

    when(menuRepository.findById(anyLong())).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      menuService.updateMenu(1L, updatedMenu, dishIds);
    });

    assertEquals("Menu not found", exception.getMessage());

    verify(menuRepository).findById(anyLong());
    verify(dishRepository, never()).findAllById(anyList());
    verify(menuRepository, never()).save(any(MenuModel.class));
  }

  @Test
  @DisplayName("Delete menu")
  void deleteMenu() {
    doNothing().when(menuRepository).deleteById(anyLong());

    menuService.deleteMenu(1L);

    verify(menuRepository).deleteById(anyLong());
  }
}