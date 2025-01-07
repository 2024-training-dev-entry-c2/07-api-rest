package com.restaurante.restaurante.repositories;

import com.restaurante.restaurante.models.Orders;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    // Para contar órdenes por cliente
    long countByClientId(Long clientId);

    // Para contar órdenes que contienen un plato específico
    long countByDishesId(Long dishId);

    // Para obtener órdenes por cliente
    List<Orders> findByClientId(Long clientId);
}
