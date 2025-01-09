package com.api_restaurant.services;

import com.api_restaurant.models.Menu;
import com.api_restaurant.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    private final MenuRepository repository;

    @Autowired
    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    public Menu addMenu(Menu menu) {
       return repository.save(menu);
    }

    public Optional<Menu> getMenu(Long id) {
        return repository.findById(id);
    }

    public List<Menu> getMenus() {
        List<Menu> menus = repository.findAll();
        for (Menu menu : menus) {
            menu.getDishes().size();
        }
        return menus;
    }

    public Menu updateMenu(Long id, Menu menu) {
        return repository.findById(id).map(x -> {
            x.setName(menu.getName());
            x.setDishes(menu.getDishes());
            return repository.save(x);
        }).orElseThrow(() -> {
            return new RuntimeException("Menu with id " + id + " could not be updated");
        });
    }

    public void deleteMenu(Long id) {
        Menu menu = repository.findById(id).orElseThrow(() -> new RuntimeException("Menu with id " + id + " not found"));
        repository.deleteById(id);
    }
}