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

    public void addDish(Dish dish, Long menuId) {
        Optional<Menu> menu = menuRepository.findById(menuId);
        menu.ifPresent(dish::setMenu);
        dishRepository.save(dish);
        menu.ifPresent(m -> {
            dish.addObserver(m);
            dish.notifyObservers();
        });
    }

    public Optional<Dish> getDish(Long id) {
        return dishRepository.findById(id);
    }

    public List<Dish> getDishes() {
        return dishRepository.findAll();
    }

    public Dish updateDish(Long id, Dish dish) {
        return dishRepository.findById(id).map(x -> {
            x.setName(dish.getName());
            x.setPrice(dish.getPrice());
            x.setDescription(dish.getDescription());
            x.setSpecialDish(dish.getSpecialDish());
            return dishRepository.save(x);
        }).orElseThrow(() -> {
            return new RuntimeException("Dish with id " + id + " could not be updated");
        });
    }

    public void deleteDish(Long id) {
        dishRepository.findById(id).ifPresent(dish -> {
            if (dish.getMenu() != null) {
                dish.removeObserver(dish.getMenu());
                dish.notifyObservers();
            }
            dishRepository.deleteById(id);
        });
    }
}