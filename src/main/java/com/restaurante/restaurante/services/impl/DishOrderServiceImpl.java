package com.restaurante.restaurante.services.impl;

import com.restaurante.restaurante.models.Dish;
import com.restaurante.restaurante.repositories.DishRepository;
import com.restaurante.restaurante.services.IDishOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DishOrderServiceImpl implements IDishOrderService {

    private final DishRepository dishRepository;

    public DishOrderServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override

    public List<Dish> processOrderDishes(List<Dish> orderDishes) {
        List<Dish> processedDishes = new ArrayList<>();

        for (Dish orderDish : orderDishes) {
            // Buscar el plato existente por ID
            Optional<Dish> existingDish = dishRepository.findById(orderDish.getId());

            if (existingDish.isPresent()) {
                Dish dish = existingDish.get();
                // Actualizar el contador de ordenes
                dish.setTotalOrdered(dish.getTotalOrdered() != null ?
                        dish.getTotalOrdered() + 1 : 1);

                // Actualizar otros campos si es necesario
                dish.setPrice(orderDish.getPrice());
                dish.setDishType(orderDish.getDishType());

                // Guardar la actualización
                processedDishes.add(dishRepository.save(dish));
            } else {
                // Si es un plato nuevo, inicializar el contador
                orderDish.setTotalOrdered(1);
                processedDishes.add(dishRepository.save(orderDish));
            }
        }

        return processedDishes;
    }
}
