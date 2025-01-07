package com.restaurante.restaurante.controllers;


import com.restaurante.restaurante.dto.DishDTO;
import com.restaurante.restaurante.services.IDishService;
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


    private final IDishService IDishService;

    @Autowired
    public DishController(IDishService IDishService){
        this.IDishService = IDishService;
    }


    @PostMapping
    public ResponseEntity<DishDTO> addDish(@RequestBody DishDTO dishDTO){

        return ResponseEntity.ok(IDishService.addDish(dishDTO));
    }

    @GetMapping("/{id}")
    public DishDTO getDish(@PathVariable Long id){
        return IDishService.getDish(id).orElseThrow();
    }

    @GetMapping
    public List<DishDTO> getDishes(){
        return IDishService.getDishes();
    }

    @PutMapping("/{id}")
    public DishDTO updateDish(@PathVariable Long id, @RequestBody DishDTO dishDTO){
        return IDishService.updateDish(id, dishDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDish(@PathVariable Long id){
        IDishService.deleteDish(id);
    }



}
