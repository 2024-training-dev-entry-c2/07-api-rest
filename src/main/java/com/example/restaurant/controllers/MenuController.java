package com.example.restaurant.controllers;

import com.example.restaurant.models.dto.menu.MenuRequestDTO;
import com.example.restaurant.models.dto.menu.MenuResponseDTO;
import com.example.restaurant.services.menu.MenuCommandHandler;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

  private final MenuCommandHandler menuCommandHandler;

  @PostMapping
  public ResponseEntity<MenuRequestDTO> createMenu(@RequestBody MenuRequestDTO menuDTO) {
    MenuResponseDTO createdMenu = menuCommandHandler.createMenu(menuDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(menuDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MenuResponseDTO> getMenuById(@PathVariable Long id) {
    MenuResponseDTO menu = menuCommandHandler.getMenuById(id);
    return ResponseEntity.ok(menu);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MenuResponseDTO> updateMenu(
          @PathVariable Long id,
          @RequestBody MenuRequestDTO menuDTO) {
    MenuResponseDTO updatedMenu = menuCommandHandler.updateMenu(id, menuDTO);
    return ResponseEntity.ok(updatedMenu);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
    menuCommandHandler.deleteMenu(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<MenuResponseDTO>> listMenus() {
    List<MenuResponseDTO> menus = menuCommandHandler.listMenus();
    return ResponseEntity.ok(menus);
  }
}
