package com.example.demo.controllers;

import com.example.demo.DTO.DishfoodRequestDTO;
import com.example.demo.DTO.DishfoodResponseDTO;
import com.example.demo.services.DishFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishfoods")
public class DishfoodController {

    @Autowired
    private DishFoodService dishfoodService;

    // Crear un nuevo plato
    @PostMapping
    public ResponseEntity<DishfoodResponseDTO> createDishfood(@RequestBody DishfoodRequestDTO dishfoodRequestDTO) {
        DishfoodResponseDTO responseDTO = dishfoodService.createDishFood(dishfoodRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // Obtener todos los platos
    @GetMapping
    public ResponseEntity<List<DishfoodResponseDTO>> getAllDishfoods() {
        List<DishfoodResponseDTO> dishfoods = dishfoodService.getAllDishfoods();
        return ResponseEntity.ok(dishfoods);
    }

    // Obtener plato por ID
    @GetMapping("/{id}")
    public ResponseEntity<DishfoodResponseDTO> getDishfoodById(@PathVariable Long id) {
        DishfoodResponseDTO responseDTO = dishfoodService.getDishfoodById(id);
        return ResponseEntity.ok(responseDTO);
    }

    // Actualizar un plato
    @PutMapping("/{id}")
    public ResponseEntity<DishfoodResponseDTO> updateDishfood(@PathVariable Long id, @RequestBody DishfoodRequestDTO dishfoodRequestDTO) {
        DishfoodResponseDTO updatedDishfood = dishfoodService.updateDishfood(id, dishfoodRequestDTO);
        return ResponseEntity.ok(updatedDishfood);
    }

    // Eliminar un plato
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDishfood(@PathVariable Long id) {
        dishfoodService.deleteDishfood(id);
        return ResponseEntity.noContent().build();
    }
}
