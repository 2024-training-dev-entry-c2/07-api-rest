package com.restaurant.management.controllers;

import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Menu;
import com.restaurant.management.models.dto.DishRequestDTO;
import com.restaurant.management.models.dto.DishResponseDTO;
import com.restaurant.management.services.DishService;
import com.restaurant.management.services.MenuService;
import com.restaurant.management.utils.DtoDishConverter;
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
  private final MenuService menuService;

  @Autowired
  public DishController(DishService service, MenuService menuService) {
    this.service = service;
    this.menuService = menuService;
  }

  @PostMapping
  public ResponseEntity<String> addDish(@RequestBody DishRequestDTO dishRequestDTO){
    try {
      Dish dish = DtoDishConverter.toDish(dishRequestDTO, null);
      menuService.addDishToMenu(dishRequestDTO.getMenuId(), dish);
      service.addDish(dish);
      return ResponseEntity.ok("Plato agregado exitosamente");
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body("Error al agregar el plato: " + e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<DishResponseDTO> getDish(@PathVariable Long id){
    return service.getDishById(id)
      .map(dish -> ResponseEntity.ok(DtoDishConverter.toDishResponseDTO(dish)))
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<DishResponseDTO>> getDishes(){
    List<DishResponseDTO> dishes = service.getDishes().stream()
      .map(DtoDishConverter::toDishResponseDTO)
      .toList();
    return ResponseEntity.ok(dishes);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateDish(@PathVariable Long id, @RequestBody DishRequestDTO dishRequestDTO){
    try{
      Menu menu = menuService.getMenuById(dishRequestDTO.getMenuId())
        .orElseThrow(() -> new RuntimeException("Menú no encontrado"));
      Dish updatedDish = service.updateDish(id, DtoDishConverter.toDish(dishRequestDTO, menu));
      return ResponseEntity.ok("Se ha actualizado exitosamente el plato.");
    } catch (RuntimeException e){
      return ResponseEntity.badRequest().body("Error al actualizar el plato: " + e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDish(@PathVariable Long id){
    service.deleteDish(id);
    return ResponseEntity.noContent().build();
  }
}
