package com.example.demo.services;

import com.example.demo.models.Dishfood;
import com.example.demo.repositories.DishfoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishFoodService {
    @Autowired
    private final DishfoodRepository repository;

    public DishFoodService(DishfoodRepository repository) {
        this.repository = repository;
    }
    public void addDishFood(Dishfood dishfood){
        repository.save(dishfood);
    }
    public Optional<Dishfood> findDishFoodId(Long id){
        return  repository.findById(id);
    }
    public List<Dishfood> findAllDishfood(){
        return repository.findAll();
    }
    public void removeDishFood(Long id){
        repository.deleteById(id);
    }
}
