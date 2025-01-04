package com.restaurante.restaurante.services;

import com.restaurante.restaurante.dto.DishDTO;
import com.restaurante.restaurante.mapper.DishMapper;
import com.restaurante.restaurante.models.Dish;
import com.restaurante.restaurante.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DishService {

    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository){
        this.dishRepository = dishRepository;
    }

    public Dish addDish(DishDTO dishDTO){

        Dish dish = DishMapper.toEntity(dishDTO);
        dishRepository.save(dish);
        return dish;
    }

    public Optional<Dish> getDish(Long id){
        return dishRepository.findById(id);
    }

    public List<Dish> getDishes(){
        return dishRepository.findAll();
    }

    public void deleteDish(Long id){
        dishRepository.deleteById(id);
    }

    public Dish updateDish(Long id, Dish dishUpdated) {
        return dishRepository.findById(id).map(x -> {
            x.setName(dishUpdated.getName());
            x.setPrice(dishUpdated.getPrice());
            x.setMenu(dishUpdated.getMenu());
            return dishRepository.save(x);
        }).orElseThrow(() -> new RuntimeException("El dish con el id " + id + "no pude ser actualizado"));


    }
}
