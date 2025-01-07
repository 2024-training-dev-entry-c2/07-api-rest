package com.api_restaurant.repositories;

import com.api_restaurant.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    long countByClientId(Long clientId);

    @Query("SELECT COUNT(o) FROM Order o JOIN o.dishes d WHERE d.id = :dishId")
    long countByDishId(@Param("dishId") Long dishId);
}