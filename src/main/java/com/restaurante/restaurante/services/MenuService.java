package com.restaurante.restaurante.services;


import com.restaurante.restaurante.dto.MenuDTO;
import com.restaurante.restaurante.mapper.MenuMapper;
import com.restaurante.restaurante.models.Menu;
import com.restaurante.restaurante.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.awt.SystemColor.menu;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    public void addMenu(MenuDTO menuDTO){
        Menu menu = MenuMapper.toEntity(menuDTO);
        menuRepository.save(menu);

    }


    public Optional<Menu> getMenu(Long id){
        return menuRepository.findById(id);
    }

    public List<Menu> getMenus(){
        return menuRepository.findAll();
    }

    public void deleteMenu(Long id){
        menuRepository.deleteById(id);
    }

    public Menu updateMenu(Long id, Menu menuUpdated){
        return menuRepository.findById(id).map(x -> {
            x.setName(menuUpdated.getName());
            x.setDescription(menuUpdated.getDescription());
            x.setDishes(menuUpdated.getDishes());
            return menuRepository.save(x);
        }).orElseThrow(() -> new RuntimeException("El menu con el id " + id +"no pude ser actualizado"));
    }




}
