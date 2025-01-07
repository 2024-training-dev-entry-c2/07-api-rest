package com.restaurant.restaurant.repositories;

import com.restaurant.restaurant.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {
  @Query("SELECT COUNT(o) FROM OrderModel o WHERE o.client.id = :clientId")
  Integer countOrdersByClientId(@Param("clientId") Long clientId);
}
