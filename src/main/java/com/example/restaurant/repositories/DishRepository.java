package com.example.restaurant.repositories;

import com.example.restaurant.models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    long countOrdersById(Long id);
}
