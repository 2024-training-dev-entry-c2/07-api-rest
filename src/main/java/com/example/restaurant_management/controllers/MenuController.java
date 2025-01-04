package com.example.restaurant_management.controllers;

import com.example.restaurant_management.dto.Menu.MenuRequestDTO;
import com.example.restaurant_management.dto.Menu.MenuResponseDTO;
import com.example.restaurant_management.models.Menu;
import com.example.restaurant_management.models.Dish;
import com.example.restaurant_management.services.MenuService;
import com.example.restaurant_management.services.DishService;
import com.example.restaurant_management.utils.MenuDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;
    private final DishService dishService;

    @Autowired
    public MenuController(MenuService menuService, DishService dishService) {
        this.menuService = menuService;
        this.dishService = dishService;
    }

    @PostMapping
    public ResponseEntity<MenuResponseDTO> addMenu(@RequestBody @Valid MenuRequestDTO menuRequestDTO) {
        List<Dish> dishes = menuRequestDTO.getDishIds().stream()
                .map(dishService::getDishById)
                .collect(Collectors.toList());
        Menu menu = MenuDtoConverter.convertToEntity(menuRequestDTO, dishes);
        menuService.addMenu(menu);
        MenuResponseDTO responseDTO = MenuDtoConverter.convertToDto(menu);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDTO> getMenuById(@PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        MenuResponseDTO responseDTO = MenuDtoConverter.convertToDto(menu);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDTO>> getAllMenus() {
        List<Menu> menus = menuService.getAllMenus();
        List<MenuResponseDTO> responseDTOs = menus.stream()
                .map(MenuDtoConverter::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuResponseDTO> updateMenu(@PathVariable Long id, @RequestBody @Valid MenuRequestDTO menuRequestDTO) {
        List<Dish> dishes = menuRequestDTO.getDishIds().stream()
                .map(dishService::getDishById)
                .collect(Collectors.toList());
        Menu menuEntity = MenuDtoConverter.convertToEntity(menuRequestDTO, dishes);
        Menu updatedMenu = menuService.updateMenu(id, menuEntity);
        MenuResponseDTO responseDTO = MenuDtoConverter.convertToDto(updatedMenu);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }
}
