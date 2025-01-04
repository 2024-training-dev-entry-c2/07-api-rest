package com.example.demo.controllers;

import com.example.demo.controllers.DTO.MenuDTO;
import com.example.demo.models.Menu;
import com.example.demo.services.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMenu(@RequestBody MenuDTO menuDTO) {
        if (menuDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        service.addMenu(Menu.builder().name(menuDTO.getName())
                        .dishfoods(menuDTO.getDishfoods())
                        .build());
        return ResponseEntity.ok("Todo oka");
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findMenuById(@PathVariable Long id) {
        Optional<Menu> menuOptional = service.findMenuById(id);
        if (menuOptional.isPresent()) {
            Menu menu = menuOptional.get();
            MenuDTO menuDTO = MenuDTO.builder()
                    .id(menu.getId())
                    .name(menu.getName())
                    .dishfoods(menu.getDishfoods())
                    .build();
            return ResponseEntity.ok(menuDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/all")
    public ResponseEntity<?> findAllMenu() {
        List<MenuDTO> menuDTOS = service.findAllMenu()
                .stream().map(menu -> MenuDTO.builder()
                        .id(menu.getId())
                        .name(menu.getName())
                        .dishfoods(menu.getDishfoods())
                        .build())
                .toList();
        return ResponseEntity.ok(menuDTOS);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeMenu(@PathVariable Long id) {
        if (service.findMenuById(id).isPresent()) {
            service.removeMenu(id);
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.notFound().build();
    }

}

