
package com.example.demo.repositories;

import com.example.demo.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    long countByClient_Id(Long clientId);
    long countByDishfoods_Id(Long dishfoodId);

}
