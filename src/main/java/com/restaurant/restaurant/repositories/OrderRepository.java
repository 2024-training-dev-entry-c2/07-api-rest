package com.restaurant.restaurant.repositories;

import com.restaurant.restaurant.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {

  @Query("SELECT COUNT(o) FROM OrderModel o WHERE o.client.id = :clientId")
  Integer countOrdersByClientId(@Param("clientId") Long clientId);
}
