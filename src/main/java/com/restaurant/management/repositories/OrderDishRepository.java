package com.restaurant.management.repositories;

import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Order;
import com.restaurant.management.models.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {
  Optional<OrderDish> findByOrderAndDish(Order order, Dish dish);

  @Query("SELECT SUM(od.quantity) FROM OrderDish od WHERE od.dish = :dish")
  Integer sumQuantityByDish(@Param("dish")Dish dish);
}
