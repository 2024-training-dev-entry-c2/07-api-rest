package com.restaurant.restaurant.controllers;

import com.restaurant.restaurant.dtos.DishDTO;
import com.restaurant.restaurant.services.DishService;
import com.restaurant.restaurant.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
public class DishController {
  private final DishService dishService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<DishDTO>>> getAllDishes(){
    List<DishDTO> dishes = dishService.findAll();
    return ResponseEntity.ok(ApiResponse.success(dishes));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<DishDTO>> getDishById(@PathVariable Long id) {
    DishDTO dish = dishService.findById(id);
    return ResponseEntity.ok(ApiResponse.success(dish));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<DishDTO>> createDish(@RequestBody DishDTO dishDTO){
    DishDTO createdDish = dishService.createDish(dishDTO);
    return new ResponseEntity<>(ApiResponse.success("Success Created Dish", createdDish), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<DishDTO>> updateDish(@PathVariable Long id, @RequestBody DishDTO dishDTO){
    DishDTO updatedDish = dishService.updateDish(id, dishDTO);
    return ResponseEntity.ok(ApiResponse.success("Success Updated Dish", updatedDish));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> deleteDish(@PathVariable Long id){
    dishService.deleteDish(id);
    return ResponseEntity.noContent().build();
  }
}
