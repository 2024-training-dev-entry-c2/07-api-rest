package com.api_restaurant.controllers;

import com.api_restaurant.dto.menu.MenuRequestDTO;
import com.api_restaurant.dto.menu.MenuResponseDTO;
import com.api_restaurant.utils.MenuDtoConvert;
import com.api_restaurant.models.Menu;
import com.api_restaurant.services.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<String> addMenu(@RequestBody MenuRequestDTO menuRequestDTO) {
        Menu menu = new Menu(
                menuRequestDTO.getName()
        );
        service.addMenu(menu);
        return ResponseEntity.ok("Menu agregado exitosamente");
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
        Menu menu = new Menu(
                menuRequestDTO.getName()
        );
        service.updateMenu(id, menu);
        return ResponseEntity.ok("Menu actualizado exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id){
        service.deleteMenu(id);
        return ResponseEntity.ok("Menu eliminado exitosamente");
    }
}