package com.api_restaurant.controllers;

import com.api_restaurant.dto.dish.DishRequestDTO;
import com.api_restaurant.dto.dish.DishResponseDTO;
import com.api_restaurant.models.Dish;
import com.api_restaurant.repositories.MenuRepository;
import com.api_restaurant.services.DishService;
import com.api_restaurant.utils.mapper.DishDtoConvert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    private final DishService service;
    private final MenuRepository menuRepository;

    public DishController(DishService service, MenuRepository menuRepository) {
        this.service = service;
        this.menuRepository = menuRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DishResponseDTO addDish(@RequestBody DishRequestDTO dishRequestDTO) {
        Dish dish = DishDtoConvert.convertToEntity(dishRequestDTO, menuRepository);
        return DishDtoConvert.convertToResponseDto(service.addDish(dish, dishRequestDTO.getMenuId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDTO> getDish(@PathVariable Long id) {
        return service.getDish(id)
                .map(dish -> ResponseEntity.ok(DishDtoConvert.convertToResponseDto(dish)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DishResponseDTO>> getDishes(){
        List<Dish> dishes = service.getDishes();
        List<DishResponseDTO> response = dishes.stream()
                .map(DishDtoConvert::convertToResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDish(@PathVariable Long id, @RequestBody DishRequestDTO dishRequestDTO) {
        try {
            Dish dish = DishDtoConvert.convertToEntity(dishRequestDTO, menuRepository);
            service.updateDish(id, dish);
            return ResponseEntity.ok("Plato actualizado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDish(@PathVariable Long id) {
        try {
            service.deleteDish(id);
            return ResponseEntity.ok("Plato eliminado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}