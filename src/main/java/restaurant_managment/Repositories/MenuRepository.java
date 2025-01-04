package restaurant_managment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant_managment.Models.MenuModel;

@Repository
public interface MenuRepository extends JpaRepository<MenuModel, Long> {
}
