package restaurant_managment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Repositories.DishRepository;
import restaurant_managment.interfaces.IDishService;

import java.util.List;
import java.util.Optional;

@Service
public class DishService implements IDishService {

  @Autowired
  private DishRepository dishRepository;

  public List<DishModel> getAllDishes() { return dishRepository.findAll(); }

  @Override
  public Optional<DishModel> getDishById(Long id) { return dishRepository.findById(id); }

  public DishModel saveDish(DishModel dish) { return dishRepository.save(dish); }

  public DishModel updateCustomer(Long id, DishModel newDish) {
    return dishRepository.findById(id)
      .map(dish -> {
        dish.setName(newDish.getName());
        dish.setPrice(newDish.getPrice());
        dish.setIsPopular(newDish.getIsPopular());
        dish.setDescription(newDish.getDescription());
        dish.setIsAvailable(newDish.getIsAvailable());
        return dishRepository.save(dish);
      })
      .orElseThrow(() -> new RuntimeException("Dish not found"));
  }

  public void deleteDish(Long id) { dishRepository.deleteById(id); }
}
