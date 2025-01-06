package restaurant_managment.Observer;

import jakarta.persistence.EntityManager;
import restaurant_managment.Models.DishModel;

import java.util.List;

public class DishObserver implements IObserver {
  private List<DishModel> dishes;
  private EntityManager entityManager;

  public DishObserver(List<DishModel> dishes, EntityManager entityManager) {
    this.dishes = dishes;
    this.entityManager = entityManager;
  }

  @Override
  public void update() {
    for (DishModel dish : dishes) {
      dish.updatePopularity(entityManager);
    }
  }
}