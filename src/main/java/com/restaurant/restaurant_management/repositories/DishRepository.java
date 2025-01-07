package com.restaurant.restaurant_management.repositories;

import com.restaurant.restaurant_management.models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
  @Query("SELECT d FROM Dish d WHERE d.menu.id = :menuId AND d.active = true")
  List<Dish> findByMenuIdAndActiveTrue(@Param("menuId") Integer menuId);
}
