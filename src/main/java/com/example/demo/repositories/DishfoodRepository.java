package com.example.demo.repositories;

import com.example.demo.models.Dishfood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishfoodRepository extends JpaRepository<Dishfood,Long> {
}
