package restaurant_managment.Proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurant_managment.Models.DishModel;
import restaurant_managment.interfaces.IDishService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class DishServiceProxy implements IDishService {

  @Autowired
  private IDishService dishService;

  private Map<Long, DishModel> dishCache = new HashMap<>();
  private List<DishModel> allDishesCache;

  @Override
  public List<DishModel> getAllDishes() {
    if (allDishesCache == null) {
      allDishesCache = dishService.getAllDishes();
    }
    return allDishesCache;
  }

  @Override
  public Optional<DishModel> getDishById(Long id) {
    if (!dishCache.containsKey(id)) {
      dishCache.put(id, dishService.getDishById(id).orElse(null));
    }
    return Optional.ofNullable(dishCache.get(id));
  }
}
