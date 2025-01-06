package com.restaurant.management.repositories;

import com.restaurant.management.models.Dish;
import com.restaurant.management.models.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {
  @Query("SELECT SUM(od.quantity) FROM OrderDish od WHERE od.dish = :dish")
  Integer sumQuantityByDish(@Param("dish")Dish dish);
}
