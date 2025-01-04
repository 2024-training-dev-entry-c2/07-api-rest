package com.restaurant.management.controllers;

import com.restaurant.management.models.Dish;
import com.restaurant.management.services.DishService;
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
@RequestMapping("api/platos")
public class DishController {
  private final DishService service;
  
  @Autowired
  public DishController(DishService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<String> addDish(@RequestBody Dish dish){
    service.addDish(dish);
    return ResponseEntity.ok("Plato agregado éxitosamente");
  }

  @GetMapping("/{id}")
  public ResponseEntity<Dish> getDish(@PathVariable Long id){
    return service.getDishById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Dish>> getDishes(){
    return ResponseEntity.ok(service.getDishes());
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateDish(@PathVariable Long id, @RequestBody Dish dish){
    try{
      Dish updatedDish = service.updateDish(id, dish);
      return ResponseEntity.ok("Se ha actualizado exitosamente el plato.");
    } catch (RuntimeException e){
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteDish(@PathVariable Long id){
    service.deleteDish(id);
    return ResponseEntity.noContent().build();
  }
}
