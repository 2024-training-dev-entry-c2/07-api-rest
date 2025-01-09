package com.example.restaurant.controllers;

import com.example.restaurant.mappers.DishMapper;
import com.example.restaurant.models.dto.dish.DishRequestDTO;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import com.example.restaurant.services.dish.DishCommandHandler;
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

@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishController {

  private final DishCommandHandler dishCommandHandler;
  private final DishMapper dishMapper;

  @PostMapping
  public ResponseEntity<DishResponseDTO> createDish(@RequestBody DishRequestDTO dishDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(dishCommandHandler.createDish(dishDTO));
  }

  @PutMapping("/{id}")
  public ResponseEntity<DishResponseDTO> updateDish(@PathVariable Long id, @RequestBody DishRequestDTO dishDTO) {
    return ResponseEntity.ok(dishCommandHandler.updateDish(id, dishDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
    dishCommandHandler.deleteDish(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<DishResponseDTO> getDishById(@PathVariable Long id) {
    return ResponseEntity.ok(dishCommandHandler.getDishById(id));
  }

  @GetMapping
  public ResponseEntity<List<DishResponseDTO>> listDishes() {
    return ResponseEntity.ok(dishCommandHandler.listDishes());
  }
}
