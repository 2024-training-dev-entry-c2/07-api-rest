package com.restaurante.restaurante.repositories;

import com.restaurante.restaurante.models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {

//    long countByOrderItemsDishId(Long dishId);
}
