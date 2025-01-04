package com.restaurante.restaurante.repositories;

import com.restaurante.restaurante.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
