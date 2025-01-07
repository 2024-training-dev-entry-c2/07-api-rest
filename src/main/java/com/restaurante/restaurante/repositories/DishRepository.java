package com.restaurante.restaurante.repositories;

import com.restaurante.restaurante.models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {
    Optional<Dish> findByName(String name);
//    long countOrdersForDishId(Long dishId);
}
