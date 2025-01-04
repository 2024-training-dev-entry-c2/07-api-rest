package com.restaurante.restaurante.controllers;


import com.restaurante.restaurante.dto.DishDTO;
import com.restaurante.restaurante.models.Dish;
import com.restaurante.restaurante.repositories.DishRepository;
import com.restaurante.restaurante.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/dishes")

public class DishController {


    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService){
        this.dishService = dishService;
    }


    @PostMapping
    public ResponseEntity<Dish> addDish(@RequestBody DishDTO dishDTO){
        return ResponseEntity.ok(dishService.addDish(dishDTO));
    }

    @GetMapping("/{id}")
    public Dish getDish(@PathVariable Long id){
        return dishService.getDish(id).orElseThrow();
    }

    @GetMapping
    public List<Dish> getDishes(){
        return dishService.getDishes();
    }

    @PutMapping("/{id}")
    public Dish updateDish(@PathVariable Long id, @RequestBody Dish dish){
        return dishService.updateDish(id, dish);
    }

    @DeleteMapping("/{id}")
    public void deleteDish(@PathVariable Long id){
        dishService.deleteDish(id);
    }



}
