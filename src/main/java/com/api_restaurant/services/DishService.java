package com.api_restaurant.services;

import com.api_restaurant.models.Dish;
import com.api_restaurant.models.Menu;
import com.api_restaurant.repositories.DishRepository;
import com.api_restaurant.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;

    public DishService(DishRepository dishRepository, MenuRepository menuRepository) {
        this.dishRepository = dishRepository;
        this.menuRepository = menuRepository;
    }

    public Dish addDish(Dish dish, Long menuId) {
        Optional<Menu> menu = menuRepository.findById(menuId);
        menu.ifPresent(dish::setMenu);
        Dish savedDish = dishRepository.save(dish);
        menu.ifPresent(m -> {
            dish.addObserver(m);
            dish.notifyObservers();
        });
        return savedDish;
    }

    public Optional<Dish> getDish(Long id) {
        return dishRepository.findById(id);
    }

    public List<Dish> getDishes() {
        return dishRepository.findAll();
    }

    public Dish updateDish(Long id, Dish dish) {
        return dishRepository.findById(id).map(existingDish -> {
            existingDish.setName(dish.getName());
            existingDish.setPrice(dish.getPrice());
            existingDish.setDescription(dish.getDescription());
            existingDish.setSpecialDish(dish.getSpecialDish());

            checkMenu(dish, existingDish);

            return dishRepository.save(existingDish);
        }).orElseThrow(() -> {
            return new RuntimeException("Dish with id " + id + " could not be updated");
        });
    }

    private static void checkMenu(Dish dish, Dish existingDish) {
        if (dish.getMenu() != null) {
            existingDish.setMenu(dish.getMenu());
        }
    }

    public void deleteDish(Long id) {
    Dish dish = dishRepository.findById(id).orElseThrow(() -> new RuntimeException("Dish with id " + id + " not found"));
    if (dish.getMenu() != null) {
        dish.removeObserver(dish.getMenu());
        dish.notifyObservers();
    }
    dishRepository.deleteById(id);
}
}