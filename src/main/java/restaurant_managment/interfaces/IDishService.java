package restaurant_managment.interfaces;

import restaurant_managment.Models.DishModel;

import java.util.Optional;

public interface IDishService {
  Optional<DishModel> getDishById(Long id);
}
