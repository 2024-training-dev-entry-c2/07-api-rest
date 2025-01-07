package com.training.restaurant.repositories;

import com.training.restaurant.models.Customer;
import com.training.restaurant.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Long countByCustomer(Customer customer);
    @Query("SELECT COUNT(od) FROM OrderDish od WHERE od.dish.id = :dishId")
    Long countDishPurchases(@Param("dishId") Long dishId);
}
