package com.example.demo.controllers;

import com.example.demo.controllers.DTO.DishfoodDTO;
import com.example.demo.models.Dishfood;
import com.example.demo.services.DishFoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dishfood")
public class DishfoodController {
    private final DishFoodService service;

    public DishfoodController(DishFoodService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDishFood(@RequestBody DishfoodDTO dishfoodDTO) {
        if (dishfoodDTO.getName().isBlank() || dishfoodDTO.getPrice()<0) {
            return ResponseEntity.badRequest().build();
        }
        service.addDishFood(Dishfood.builder().name(dishfoodDTO.getName())
                        .price(dishfoodDTO.getPrice())
                        .menu(dishfoodDTO.getMenu())
                        .build());

        return ResponseEntity.ok("Todo oka");
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findDishFoodById(@PathVariable Long id) {
        Optional<Dishfood> dishfoodOptional = service.findDishFoodId(id);
        if (dishfoodOptional.isPresent()) {
            Dishfood dishfood = dishfoodOptional.get();
            DishfoodDTO dishfoodDTO = DishfoodDTO.builder()
                    .id(dishfood.getId())
                    .name(dishfood.getName())
                    .price(dishfood.getPrice())
                    .menu(dishfood.getMenu())
                    .build();
            return ResponseEntity.ok(dishfoodDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll() {
        List<DishfoodDTO> dishfoodDTOS = service.findAllDishfood()
                .stream().map(dishfood -> DishfoodDTO.builder()
                        .id(dishfood.getId())
                        .name(dishfood.getName())
                        .price(dishfood.getPrice())
                        .menu(dishfood.getMenu())
                        .build())
                .toList();
        return ResponseEntity.ok(dishfoodDTOS);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeDishfood(@PathVariable Long id) {
        if (service.findDishFoodId(id).isPresent()) {
            service.removeDishFood(id);
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.notFound().build();
    }

}

