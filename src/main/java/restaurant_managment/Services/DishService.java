package restaurant_managment.Services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Repositories.DishRepository;
import restaurant_managment.interfaces.IDishService;

import java.util.List;
import java.util.Optional;

@Service
public class DishService implements IDishService {

  private final DishRepository dishRepository;
  private final EntityManager entityManager;

  @Autowired
  public DishService(DishRepository dishRepository, EntityManager entityManager) {
    this.dishRepository = dishRepository;
    this.entityManager = entityManager;
  }

  public List<DishModel> getAllDishes() {
    return dishRepository.findAll();
  }

  @Override
  public Optional<DishModel> getDishById(Long id) {
    return dishRepository.findById(id);
  }

  @Transactional
  public DishModel saveDish(DishModel dish) {
    DishModel savedDish = dishRepository.save(dish);
    updateDishPopularity(savedDish);
    return savedDish;
  }

  @Transactional
  public DishModel updateDish(Long id, DishModel newDish) {
    return dishRepository.findById(id)
      .map(dish -> {
        dish.setName(newDish.getName());
        dish.setPrice(newDish.getPrice());
        dish.setIsPopular(newDish.getIsPopular());
        dish.setDescription(newDish.getDescription());
        dish.setIsAvailable(newDish.getIsAvailable());

        DishModel updatedDish = dishRepository.save(dish);

        updateDishPopularity(updatedDish);

        return updatedDish;
      })
      .orElseThrow(() -> new RuntimeException("Dish not found"));
  }

  public void deleteDish(Long id) {
    dishRepository.deleteById(id);
  }

  private void updateDishPopularity(DishModel dish) {
    dish.updatePopularity(entityManager);
  }
}