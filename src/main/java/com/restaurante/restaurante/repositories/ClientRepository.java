package com.restaurante.restaurante.repositories;

import com.restaurante.restaurante.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
