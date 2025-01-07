package com.restaurant.restaurant.controllers;

import com.restaurant.restaurant.dtos.DishDTO;
import com.restaurant.restaurant.models.DishModel;
import com.restaurant.restaurant.services.DishService;
import com.restaurant.restaurant.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DishController {
  @Autowired
  private DishService dishService;

  @PostMapping
  public ResponseEntity<DishDTO> createDish(@RequestBody DishDTO dishDTO){
    DishModel dishModel = MapperUtil.mapToDishModel(dishDTO);
    DishModel createdDish = dishService.createDish(dishModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(MapperUtil.mapToDishDTO(createdDish));
  }

  @GetMapping
  public ResponseEntity<List<DishDTO>> getDishes(){
    List<DishModel> dishes = dishService.getDishes();
    List<DishDTO> dishDTOs = dishes.stream().map(MapperUtil::mapToDishDTO).collect(Collectors.toList());
    return ResponseEntity.ok(dishDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DishDTO> updateDish(@PathVariable Long id, @RequestBody DishDTO dishDTO){
    DishModel dish = MapperUtil.mapToDishModel(dishDTO);
    DishModel updatedDish = dishService.updateDish(id, dish);
    return ResponseEntity.ok(MapperUtil.mapToDishDTO(updatedDish));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDish(@PathVariable Long id){
    dishService.deleteDish(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("{id}/verify-popular")
  public ResponseEntity<Void> verifyPopular(@PathVariable Long id){
    dishService.verifyPopular(id);
    return ResponseEntity.ok().build();
  }
}
