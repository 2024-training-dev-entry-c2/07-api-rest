package restaurant_managment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant_managment.Models.OrderModel;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
}