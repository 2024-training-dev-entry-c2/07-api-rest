package com.example.demo.services;

import com.example.demo.models.Menu;
import com.example.demo.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }
    public void addMenu(Menu menu){
        repository.save(menu);
    }
    public Optional<Menu> findMenuById(Long id){
        return repository.findById(id);
    }
    public List<Menu> findAllMenu(){
        return repository.findAll();
    }
    public void removeMenu(Long id){
        repository.deleteById(id);
    }

}
