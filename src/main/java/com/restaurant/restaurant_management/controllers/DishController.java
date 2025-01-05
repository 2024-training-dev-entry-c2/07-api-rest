package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.dto.DishRequestDTO;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.models.Menu;
import com.restaurant.restaurant_management.services.DishService;
import com.restaurant.restaurant_management.services.MenuService;
import com.restaurant.restaurant_management.utils.DtoDishConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dish")
public class DishController {
  private final DishService dishService;
  private final MenuService menuService;

  public DishController(DishService dishService, MenuService menuService) {
    this.dishService = dishService;
    this.menuService = menuService;
  }

  @PostMapping
  public ResponseEntity<String> saveDish(@RequestBody DishRequestDTO dishRequestDTO) {
    Menu menu = menuService.getMenu(dishRequestDTO.getMenuId()).orElse(null);
    if (menu == null) {
      return ResponseEntity.badRequest().body("Menu not found");
    }
    Dish dish = DtoDishConverter.convertToDish(dishRequestDTO, menu);
    dishService.saveDish(dish);
    return ResponseEntity.ok("Dish created successfully");
  }

  @GetMapping
  public ResponseEntity<List<Dish>> getDishes() {
    return ResponseEntity.ok(dishService.listDishes());
  }

  @GetMapping("/menu/{menuId}")
  public ResponseEntity<List<Dish>> getDishesByMenuId(@PathVariable Integer menuId) {
    return ResponseEntity.ok(dishService.listDishesByMenuId(menuId));
  }
}
