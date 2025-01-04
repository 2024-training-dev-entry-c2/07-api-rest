package com.restaurant.management.controllers;

import com.restaurant.management.models.Menu;
import com.restaurant.management.services.MenuService;
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
  public ResponseEntity<String> addMenu(@RequestBody Menu menu){
    service.addMenu(menu);
    return ResponseEntity.ok("Menú agregado éxitosamente");
  }

  @GetMapping("/{id}")
  public ResponseEntity<Menu> getMenu(@PathVariable Long id){
    return service.getMenuById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Menu>> getMenus(){
    return ResponseEntity.ok(service.getMenus());
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateMenu(@PathVariable Long id, @RequestBody Menu menu){
    try{
      Menu updatedMenu = service.updateMenu(id, menu);
      return ResponseEntity.ok("Se ha actualizado exitosamente el menú.");
    } catch (RuntimeException e){
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteMenu(@PathVariable Long id){
    service.deleteMenu(id);
    return ResponseEntity.noContent().build();
  }
  
}
