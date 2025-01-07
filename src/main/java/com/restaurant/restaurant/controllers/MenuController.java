package com.restaurant.restaurant.controllers;

import com.restaurant.restaurant.dtos.MenuDTO;
import com.restaurant.restaurant.models.MenuModel;
import com.restaurant.restaurant.services.MenuService;
import com.restaurant.restaurant.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
  @Autowired
  private MenuService menuService;

  @PostMapping
  public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menuDTO){
    MenuModel menuModel = MapperUtil.mapToMenuModel(menuDTO);
    MenuModel createdMenu = menuService.createMenu(menuModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(MapperUtil.mapToMenuDTO(createdMenu));
  }

  @GetMapping
  public ResponseEntity<List<MenuDTO>> getMenus(){
    List<MenuModel> menus = menuService.getMenus();
    List<MenuDTO> menuDTOs = menus.stream().map(MapperUtil::mapToMenuDTO).collect(Collectors.toList());
    return ResponseEntity.ok(menuDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MenuDTO> updateMenu(@PathVariable Long id, @RequestBody MenuDTO menuDTO){
    MenuModel menu = MapperUtil.mapToMenuModel(menuDTO);
    MenuModel updatedMenu = menuService.updateMenu(id, menu);
    return ResponseEntity.ok(MapperUtil.mapToMenuDTO(updatedMenu));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMenu(@PathVariable Long id){
    menuService.deleteMenu(id);
    return ResponseEntity.noContent().build();
  }
}
