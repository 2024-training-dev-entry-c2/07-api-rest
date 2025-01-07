package com.restaurante.restaurante.controllers;


import com.restaurante.restaurante.dto.MenuDTO;
import com.restaurante.restaurante.services.IMenuService;
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

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final IMenuService IMenuService;

    @Autowired
    public MenuController(IMenuService IMenuService){
        this.IMenuService = IMenuService;
    }



    @PostMapping
    public ResponseEntity<String> addMenu(@RequestBody MenuDTO menuDTO){
        IMenuService.addMenu(menuDTO);
        return ResponseEntity.ok("Menu agregado exitosamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> getMenu(@PathVariable Long id){
        return ResponseEntity.ok(IMenuService.getMenu(id).orElseThrow());
    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getMenus(){
        return ResponseEntity.ok(IMenuService.getMenus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMenu(@PathVariable Long id, @RequestBody MenuDTO menuDTO){
        try{
            MenuDTO menuUpdated = IMenuService.updateMenu(id, menuDTO);
            return ResponseEntity.ok("Se ha actualizado exitosamente el menu");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id){
        IMenuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }



}
