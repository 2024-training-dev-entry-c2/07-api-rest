package com.restaurante.restaurante.repositories;

import com.restaurante.restaurante.models.Orders;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByClientId(Long clientId, Sort orderDate);
    long countByClientId(Long clientId);
}
