package restaurant_managment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant_managment.Models.DishModel;

@Repository
public interface DishRepository extends JpaRepository<DishModel, Long> {
}
