package com.example.demo.repositories;

import com.example.demo.models.Clientorder;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository <Clientorder,Long>{

}
