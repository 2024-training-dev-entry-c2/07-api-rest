package com.restaurant.restaurant.repositories;

import com.restaurant.restaurant.models.DishModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DishRepository extends JpaRepository<DishModel, Long> {
  @Query("SELECT COUNT(o) FROM orders o JOIN o.dishes dish WHERE dish.id = :dishId")
  Integer countOrdersByDishId(@Param("dishId") Long dishId);
}
