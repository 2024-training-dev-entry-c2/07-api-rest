package com.example.restaurant.services.dish;

import com.example.restaurant.constants.DishType;
import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import com.example.restaurant.observers.DishSubject;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IsPopularDishService implements ICommandParametrized<Void, List<Dish>> {

    private final IDishRepository dishRepository;
    private final IOrderRepository orderRepository;
    private final DishSubject dishSubject;

    @Autowired
    public IsPopularDishService(IDishRepository dishRepository, IOrderRepository orderRepository, DishSubject dishSubject) {
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
        this.dishSubject = dishSubject;
    }

    @Override
    public Void execute(List<Dish> dishes) {
        dishes.forEach(dish -> {
            Long orderCount = orderRepository.countByDishesId(dish.getId());
            markIsPopular(dish, orderCount);
        });
        return null;
    }

    private void markIsPopular(Dish dish, Long orderCount) {
        if (orderCount >= 100) {
            dish.setDishType(DishType.POPULAR);
            dishRepository.save(dish);
            dishSubject.notifyObservers(EventType.UPDATE, dish);
        }
    }
}
