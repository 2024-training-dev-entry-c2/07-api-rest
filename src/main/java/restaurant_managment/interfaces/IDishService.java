package restaurant_managment.interfaces;

import restaurant_managment.Models.DishModel;

import java.util.List;
import java.util.Optional;

public interface IDishService {
  List<DishModel> getAllDishes();
  Optional<DishModel> getDishById(Long id);
}
