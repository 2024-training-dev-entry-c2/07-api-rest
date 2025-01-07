package com.training.restaurant.controllers;

import com.training.restaurant.dto.menus.CreateMenuDto;
import com.training.restaurant.dto.menus.MenuDto;
import com.training.restaurant.dto.menus.UpdateMenuDto;
import com.training.restaurant.services.menus.MenuDishServiceImpl;
import com.training.restaurant.services.menus.MenuServiceImpl;
import com.training.restaurant.utils.MenuConverter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    private final Logger logger = LoggerFactory.getLogger(MenuController.class);
    private final MenuDishServiceImpl menuDishService;
    private final MenuServiceImpl menuService;

    @Autowired
    public MenuController(MenuServiceImpl menuService, MenuDishServiceImpl menuDishService){
        this.menuDishService = menuDishService;
        this.menuService = menuService;
    }


    @PostMapping
    public ResponseEntity<MenuDto> createMenu(@RequestBody @Valid CreateMenuDto createMenuDto) {
        logger.info("Pattern facade in action yum yum - creating a new menu with dishes: " + createMenuDto.name());
        MenuDto menu = MenuConverter.toMenuDto(menuDishService.createMenuWithDishes(createMenuDto));
        URI uri = UriComponentsBuilder.fromUriString("/menu/{id}").buildAndExpand(menu.id()).toUri();
        return ResponseEntity.created(uri).body(menu);
    }

    @GetMapping
    public ResponseEntity<List<MenuDto>> findAllMenus() {
        return ResponseEntity.ok(menuService.findAllMenus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> findMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(MenuConverter.toMenuDto(menuService.findMenuById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMenu(@PathVariable Long id,
                                             @Valid @RequestBody UpdateMenuDto updateMenuDto) {
        menuService.updateMenu(id, updateMenuDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.removeMenu(id);
        return ResponseEntity.noContent().build();
    }

}
