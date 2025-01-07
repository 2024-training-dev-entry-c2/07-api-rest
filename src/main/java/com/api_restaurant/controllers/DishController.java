package com.api_restaurant.controllers;

import com.api_restaurant.dto.dish.DishRequestDTO;
import com.api_restaurant.dto.dish.DishResponseDTO;
import com.api_restaurant.models.Dish;
import com.api_restaurant.services.DishService;
import com.api_restaurant.utils.DishDtoConvert;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    private final DishService service;
    private final DishDtoConvert dishDtoConvert;

    public DishController(DishService service, DishDtoConvert dishDtoConvert) {
        this.service = service;
        this.dishDtoConvert = dishDtoConvert;
    }

    @PostMapping
    public ResponseEntity<String> addDish(@RequestBody DishRequestDTO dishRequestDTO) {
        Dish dish = dishDtoConvert.convertToEntity(dishRequestDTO);
        service.addDish(dish, dishRequestDTO.getMenuId());
        return ResponseEntity.ok("Plato agregado exitosamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDTO> getDish(@PathVariable Long id) {
        return service.getDish(id)
                .map(dish -> ResponseEntity.ok(dishDtoConvert.convertToResponseDto(dish)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DishResponseDTO>> getDishes(){
        List<Dish> dishes = service.getDishes();
        List<DishResponseDTO> response = dishes.stream()
                .map(dishDtoConvert::convertToResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDish(@PathVariable Long id, @RequestBody DishRequestDTO dishRequestDTO) {
        Dish dish = dishDtoConvert.convertToEntity(dishRequestDTO);
        service.updateDish(id, dish);
        return ResponseEntity.ok("Plato actualizado exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDish(@PathVariable Long id) {
        service.deleteDish(id);
        return ResponseEntity.ok("Plato eliminado exitosamente");
    }
}