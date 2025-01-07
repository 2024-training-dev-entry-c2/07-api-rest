package com.restaurant.restaurant.repositories;

import com.restaurant.restaurant.models.MenuModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuModel, Long> {
}
