package com.restaurant.restaurant.services;

import com.restaurant.restaurant.factories.DishFactory;
import com.restaurant.restaurant.models.DishModel;
import com.restaurant.restaurant.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
  @Autowired
  private DishRepository dishRepository;

  @Autowired
  private DishFactory dishFactory;

  public DishModel createDish(DishModel dishModel){
    return dishRepository.save(dishModel);
  }

  public List<DishModel> getDishes(){
    return dishRepository.findAll();
  }

  public DishModel updateDish(Long id, DishModel dish){
    return dishRepository.findById(id).map(x -> {
      x.setName(dish.getName());
      x.setPrice(dish.getPrice());
      x.setIsPopular(dish.getIsPopular());
      return dishRepository.save(x);
    }).orElseThrow(() -> new RuntimeException("Dish with id " + id + " not found"));
  }

  public void deleteDish(Long id){
    dishRepository.deleteById(id);
  }

  public void verifyPopular(Long dishId){
    Integer totalOrders = dishRepository.countOrdersByDishId(dishId);
    if(totalOrders > 100){
      DishModel dish = dishRepository.findById(dishId).orElseThrow(() -> new RuntimeException("Dish with id " + dishId + " not found"));
      dish.setIsPopular(true);
      dish.setPrice(dish.getPrice()*1.5);
      dishRepository.save(dish);
    }
  }
}
