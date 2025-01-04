package com.restaurant.management.repositories;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  Integer countByClient(Client client);
  Integer countByDishesContaining(Dish dish);
}
