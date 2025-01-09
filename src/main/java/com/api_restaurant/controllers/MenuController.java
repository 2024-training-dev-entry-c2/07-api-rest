package com.api_restaurant.controllers;

import com.api_restaurant.dto.menu.MenuRequestDTO;
import com.api_restaurant.dto.menu.MenuResponseDTO;
import com.api_restaurant.utils.mapper.MenuDtoConvert;
import com.api_restaurant.models.Menu;
import com.api_restaurant.services.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService service;
    private final MenuDtoConvert menuDtoConvert;

    public MenuController(MenuService service, MenuDtoConvert menuDtoConvert) {
        this.service = service;
        this.menuDtoConvert = menuDtoConvert;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuResponseDTO addMenu(@RequestBody MenuRequestDTO menuRequestDTO) {
        Menu menu = new Menu(menuRequestDTO.getName());
        return menuDtoConvert.convertToResponseDto(service.addMenu(menu));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDTO> getMenu(@PathVariable Long id){
        return service.getMenu(id)
                .map(menu -> ResponseEntity.ok(menuDtoConvert.convertToResponseDto(menu)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDTO>> getMenus(){
        List<Menu> menus = service.getMenus();
        List<MenuResponseDTO> response = menus.stream()
                .map(menuDtoConvert::convertToResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMenu(@PathVariable Long id, @RequestBody MenuRequestDTO menuRequestDTO){
        try {
            Menu menu = new Menu(menuRequestDTO.getName());
            service.updateMenu(id, menu);
            return ResponseEntity.ok("Menu actualizado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id){
        service.deleteMenu(id);
        return ResponseEntity.ok("Menu eliminado exitosamente");
    }
}