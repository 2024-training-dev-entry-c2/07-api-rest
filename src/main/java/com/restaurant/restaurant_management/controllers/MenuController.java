package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.dto.MenuRequestDTO;
import com.restaurant.restaurant_management.dto.MenuResponseDTO;
import com.restaurant.restaurant_management.models.Menu;
import com.restaurant.restaurant_management.services.MenuService;
import com.restaurant.restaurant_management.utils.DtoMenuConverter;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/menu")
public class MenuController {
  private final MenuService menuService;

  public MenuController(MenuService menuService) {
    this.menuService = menuService;
  }

  @PostMapping
  public ResponseEntity<String> saveMenu(@RequestBody MenuRequestDTO menuRequestDTO) {
    Menu menu = DtoMenuConverter.convertToMenu(menuRequestDTO);
    menuService.saveMenu(menu);
    return ResponseEntity.ok("Menú creado con éxito");
  }

  @GetMapping("/{id}")
  public ResponseEntity<MenuResponseDTO> getMenu(@PathVariable Integer id) {
    return menuService.getMenu(id)
        .map(menu -> ResponseEntity.ok(DtoMenuConverter.convertToResponseDTO(menu)))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<MenuResponseDTO>> getMenus() {
    List<Menu> menus = menuService.listMenus();
    List<MenuResponseDTO> response = menus.stream()
        .map(DtoMenuConverter::convertToResponseDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MenuResponseDTO> updateMenu(@PathVariable Integer id, @RequestBody MenuRequestDTO menuRequestDTO) {
    try {
      Menu updated = menuService.updateMenu(id, DtoMenuConverter.convertToMenu(menuRequestDTO));
      return ResponseEntity.ok(DtoMenuConverter.convertToResponseDTO(updated));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

}
