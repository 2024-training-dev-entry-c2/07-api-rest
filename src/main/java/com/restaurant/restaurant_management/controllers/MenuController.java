package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.models.Menu;
import com.restaurant.restaurant_management.services.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
  private final MenuService menuService;

  public MenuController(MenuService menuService) {
    this.menuService = menuService;
  }

  @PostMapping
  public ResponseEntity<String> saveMenu(@RequestBody Menu menu) {
    menuService.saveMenu(menu);
    return ResponseEntity.ok("Menú creado con éxito");
  }

  @GetMapping
  public ResponseEntity<List<Menu>> getMenus() {
    return ResponseEntity.ok(menuService.listMenus());
  }
}
