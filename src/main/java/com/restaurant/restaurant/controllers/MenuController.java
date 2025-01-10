package com.restaurant.restaurant.controllers;

import com.restaurant.restaurant.dtos.MenuDTO;
import com.restaurant.restaurant.services.MenuService;
import com.restaurant.restaurant.utils.ApiResponse;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {
  private final MenuService menuService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<MenuDTO>>> getAllMenus(){
    List<MenuDTO> menus = menuService.findAll();
    return ResponseEntity.ok(ApiResponse.success(menus));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<MenuDTO>> getMenuById(@PathVariable Long id) {
    MenuDTO menu = menuService.findById(id);
    return ResponseEntity.ok(ApiResponse.success(menu));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<MenuDTO>> createMenu(@RequestBody MenuDTO menuDTO){
    MenuDTO createdMenu = menuService.createMenu(menuDTO);
    return new ResponseEntity<>(ApiResponse.success("Success Created Menu", createdMenu), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<MenuDTO>> updateMenu(@PathVariable Long id, @RequestBody MenuDTO menuDTO){
    MenuDTO updatedMenu = menuService.updateMenu(id, menuDTO);
    return ResponseEntity.ok(ApiResponse.success("Success Updated Menu", updatedMenu));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> deleteMenu(@PathVariable Long id){
    menuService.deleteMenu(id);
    return ResponseEntity.noContent().build();
  }
}
