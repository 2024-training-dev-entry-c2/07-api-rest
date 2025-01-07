package com.training.restaurant.controllers;

import com.training.restaurant.dto.dishes.CreateDishDto;
import com.training.restaurant.dto.dishes.DishesDto;
import com.training.restaurant.dto.dishes.UpdateDishDto;
import com.training.restaurant.services.dishes.DishServiceImpl;
import com.training.restaurant.services.menus.MenuDishServiceImpl;
import com.training.restaurant.utils.DishesConverter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/dishes")
public class DishesController {

    private static final Logger logger = LoggerFactory.getLogger(DishesController.class);
    private MenuDishServiceImpl menuDishService;
    private DishServiceImpl dishService;

    public DishesController(DishServiceImpl dishService, MenuDishServiceImpl menuDishService){
        this.menuDishService = menuDishService;
        this.dishService = dishService;
    }
    @PostMapping("/menu/{menuId}")
    public ResponseEntity<DishesDto> createDish(@PathVariable Long menuId, @Valid @RequestBody CreateDishDto createDishDto) {
        logger.info("Creating a new dish: " + createDishDto.name());
        DishesDto dish = DishesConverter.toDishesDto(menuDishService.createDishForExistingMenu(menuId, createDishDto));
        URI uri = UriComponentsBuilder.fromUriString("/dishes/{id}").buildAndExpand(dish.id()).toUri();
        return ResponseEntity.created(uri).body(dish);
    }

    @GetMapping
    public ResponseEntity<List<DishesDto>> findAllDishes() {
        return ResponseEntity.ok(DishesConverter.toDishesDtoList(dishService.findAllDishes()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishesDto> findDishById(@PathVariable Long id) {
        return ResponseEntity.ok(DishesConverter.toDishesDto(dishService.findDishById(id)));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDish(@PathVariable Long id, @RequestBody UpdateDishDto updateDishDto){
        dishService.updateDish(id, updateDishDto);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id){
        dishService.removeDish(id);
        return ResponseEntity.noContent().build();
    }
}
