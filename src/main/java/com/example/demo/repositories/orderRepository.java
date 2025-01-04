
package com.example.demo.repositories;

import com.example.demo.models.Menu;
import com.example.demo.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface orderRepository extends JpaRepository<Order,Long> {

}
