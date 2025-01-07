package com.training.restaurant.repositories;

import com.training.restaurant.models.Dishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishesRepository extends JpaRepository<Dishes,Long> {

    Optional<Dishes> findByName(String name);
}
