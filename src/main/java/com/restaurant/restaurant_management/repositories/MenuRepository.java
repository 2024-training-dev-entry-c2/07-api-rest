package com.restaurant.restaurant_management.repositories;

import com.restaurant.restaurant_management.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
  List<Menu> findByActiveTrue();
}
