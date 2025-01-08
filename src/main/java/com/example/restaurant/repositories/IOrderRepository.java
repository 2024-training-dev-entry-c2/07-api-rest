package com.example.restaurant.repositories;

import com.example.restaurant.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    Long countByDishesId(Long dishId);

    Long countByClientId(Long clientId);
}

