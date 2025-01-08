package com.example.restaurant.services.dish;

import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.services.interfaces.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllDishesService implements ICommand<List<Dish>> {

    private final IDishRepository dishRepository;

    @Autowired
    public GetAllDishesService(IDishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Dish> execute() {
        return dishRepository.findAll();
    }
}
