package com.example.demo.controllers;

import com.example.demo.DTO.MenuRequestDTO;
import com.example.demo.DTO.MenuResponseDTO;
import com.example.demo.services.MenuService;
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
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService service) {
        this.menuService = service;
    }

    @PostMapping
    public ResponseEntity<MenuResponseDTO> addMenu(@RequestBody MenuRequestDTO menuDTO) {
        return ResponseEntity.ok(menuService.createMenu(menuDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDTO> findMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDTO>> findAllMenu() {
        return ResponseEntity.ok(menuService.getAllMenus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuResponseDTO> updateMenu(@PathVariable Long id, @RequestBody MenuRequestDTO dto) {
        return ResponseEntity.ok(menuService.updateMenu(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MenuResponseDTO> removeMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }

}

