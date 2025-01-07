package com.training.restaurant.repositories;

import com.training.restaurant.models.Customer;
import com.training.restaurant.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    long countByCustomer(Customer customer);
}
