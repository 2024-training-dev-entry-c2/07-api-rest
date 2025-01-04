package com.example.demo.repositories;

import com.example.demo.models.Clientorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface clientRepository extends JpaRepository <Clientorder,Long>{
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.cliente.id = :clienteId")
    int countOrderByClient(@Param("clienteId") Long clienteId);
}
