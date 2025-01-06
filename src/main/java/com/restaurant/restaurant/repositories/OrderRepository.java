package com.restaurant.restaurant.repositories;

import com.restaurant.restaurant.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {
}
