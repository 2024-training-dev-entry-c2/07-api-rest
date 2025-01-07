package com.training.restaurant.services.dishes;

import com.training.restaurant.dto.dishes.UpdateDishDto;
import com.training.restaurant.models.DishType;
import com.training.restaurant.models.Dishes;
import com.training.restaurant.repositories.DishesRepository;
import com.training.restaurant.services.interfaces.IDishService;
import com.training.restaurant.utils.exceptions.BadRequestException;
import com.training.restaurant.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements IDishService {

    private final DishesRepository dishesRepository;

    @Autowired
    public DishServiceImpl(DishesRepository dishesRepository) {
        this.dishesRepository = dishesRepository;
    }

    @Override
    public Dishes createDish(Dishes dishes) {
        try{
            return dishesRepository.save(dishes);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Ocurrio un error al intentar guardar el plato");
        }
    }

    @Override
    public List<Dishes> findAllDishes() {
        return dishesRepository.findAll();
    }

    @Override
    public List<Dishes> createAllDishes(List<Dishes> dishes){
        try{
            return dishesRepository.saveAll(dishes);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Ocurrio un error al intentar guardar la lista de platos");
        }
    }

    @Override
    public Dishes findDishById(Long id) {
        return dishesRepository.findById(id).orElseThrow(() -> new NotFoundException("El plato no existe"));
    }
    @Override
    public Dishes updateDish(Long id, UpdateDishDto updateDishDto) {
        Dishes dishes = findDishById(id);
        updateDishFields(dishes, updateDishDto);
        return dishesRepository.save(dishes);
    }
    @Override
    public void removeDish(Long id) {
        Dishes dish = findDishById(id);
        dishesRepository.delete(dish);
    }

    @Override
    public Dishes findDishByName(String name){
        return dishesRepository.findByName(name).orElseThrow(() -> new NotFoundException("El plato no existe"));
    }

    private void updateDishFields(Dishes dishes, UpdateDishDto updateDishDto) {
        updateNameIfPresent(dishes, updateDishDto.name());
        updateTypeIfPresent(dishes, updateDishDto.type());
        updatePriceIfPresent(dishes, updateDishDto.price());
    }

    private void updateNameIfPresent(Dishes dishes, String name) {
        if (name != null) {
            dishes.setName(name);
        }
    }

    private void updateTypeIfPresent(Dishes dishes, String type) {
        if (type != null) {
            dishes.setType(DishType.fromString(type));
        }
    }

    private void updatePriceIfPresent(Dishes dishes, Double price) {
        if (price != null) {
            dishes.setPrice(price);
        }
    }
}
