package com.restaurant.management.controllers;

import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Menu;
import com.restaurant.management.models.dto.MenuRequestDTO;
import com.restaurant.management.models.dto.MenuResponseDTO;
import com.restaurant.management.services.MenuService;
import com.restaurant.management.utils.DtoMenuConverter;
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
@RequestMapping("api/menus")
public class MenuController {
  private final MenuService service;
  
  @Autowired
  public MenuController(MenuService service) {
    this.service = service;
  }
  
  @PostMapping
  public ResponseEntity<String> addMenu(@RequestBody MenuRequestDTO menuRequestDTO){
    service.addMenu(DtoMenuConverter.toMenu(menuRequestDTO));
    return ResponseEntity.ok("Menú agregado éxitosamente");
  }

  @GetMapping("/{id}")
  public ResponseEntity<MenuResponseDTO> getMenu(@PathVariable Long id){
    return service.getMenuById(id)
      .map(menu -> ResponseEntity.ok(DtoMenuConverter.toMenuResponseDTO(menu)))
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<MenuResponseDTO>> getMenus(){
    List<MenuResponseDTO> menus = service.getMenus().stream()
      .map(DtoMenuConverter::toMenuResponseDTO)
      .toList();
    return ResponseEntity.ok(menus);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateMenu(@PathVariable Long id, @RequestBody MenuRequestDTO menuRequestDTO){
    try{
      Menu updatedMenu = service.updateMenu(id, DtoMenuConverter.toMenu(menuRequestDTO));
      return ResponseEntity.ok("Se ha actualizado exitosamente el menú.");
    } catch (RuntimeException e){
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMenu(@PathVariable Long id){
    service.deleteMenu(id);
    return ResponseEntity.noContent().build();
  }


}
