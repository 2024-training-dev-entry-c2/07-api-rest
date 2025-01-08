package com.example.restaurant.services.dish;

import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetDishByIdService implements ICommandParametrized<Dish, Long> {

    private final IDishRepository dishRepository;

    @Autowired
    public GetDishByIdService(IDishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Dish execute(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato con el id " + id + " no encontrado"));
    }
}
