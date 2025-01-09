package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.dto.DishRequestDTO;
import com.restaurant.restaurant_management.dto.DishResponseDTO;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.models.Menu;
import com.restaurant.restaurant_management.services.DishService;
import com.restaurant.restaurant_management.services.MenuService;
import com.restaurant.restaurant_management.utils.DtoDishConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public ResponseEntity<DishResponseDTO> saveDish(@RequestBody DishRequestDTO dishRequestDTO) {
    try {
      Menu menu = menuService.getMenu(dishRequestDTO.getMenuId()).orElseThrow();
      Dish dish = DtoDishConverter.convertToDish(dishRequestDTO, menu);
      return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(DtoDishConverter.convertToResponseDTO(dishService.saveDish(dish)));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<DishResponseDTO> getDish(@PathVariable Integer id) {
    return dishService.getDish(id)
        .map(dish -> ResponseEntity.ok(DtoDishConverter.convertToResponseDTO(dish)))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<DishResponseDTO>> getDishes() {
    List<Dish> dishes = dishService.listDishes();
    List<DishResponseDTO> response = dishes.stream()
        .map(DtoDishConverter::convertToResponseDTO)
        .toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/menu/{menuId}")
  public ResponseEntity<List<DishResponseDTO>> getDishesByMenuIdAndActive(@PathVariable Integer menuId) {
    List<Dish> dishes = dishService.listDishesByMenuIdAndActive(menuId);
    List<DishResponseDTO> response = dishes.stream()
        .map(DtoDishConverter::convertToResponseDTO)
        .toList();
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DishResponseDTO> updateDish(@PathVariable Integer id, @RequestBody DishRequestDTO dishRequestDTO) {
    try {
      Menu menu = menuService.getMenu(dishRequestDTO.getMenuId()).orElseThrow();
      Dish updated = dishService.updateDish(id, DtoDishConverter.convertToDish(dishRequestDTO, menu));
      return ResponseEntity.ok(DtoDishConverter.convertToResponseDTO(updated));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

}
