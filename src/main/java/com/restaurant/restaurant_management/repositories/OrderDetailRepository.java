package com.restaurant.restaurant_management.repositories;

import com.restaurant.restaurant_management.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

  @Query("SELECT d FROM OrderDetail d WHERE d.clientOrder.id = :orderId")
  List<OrderDetail> findByOrderId(@Param("orderId") Long orderId);

  Long countByDishId(Integer dishId);
}
