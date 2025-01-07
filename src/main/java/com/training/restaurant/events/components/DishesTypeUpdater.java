package com.training.restaurant.events.components;

import com.training.restaurant.events.NewOrderEvent;
import com.training.restaurant.models.DishType;
import com.training.restaurant.models.Dishes;
import com.training.restaurant.models.OrderDish;
import com.training.restaurant.models.Orders;
import com.training.restaurant.repositories.DishesRepository;
import com.training.restaurant.services.orders.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DishesTypeUpdater {

    private final DishesRepository dishesRepository;
    private final OrderServiceImpl orderService;

    @Autowired
    public DishesTypeUpdater(DishesRepository dishRepository, OrderServiceImpl orderService) {
        this.dishesRepository = dishRepository;
        this.orderService = orderService;
    }

    @EventListener
    public void handleNewDishEvent(NewOrderEvent event) {
        Orders order = event.getOrder();
        List<OrderDish> orderDishes = order.getOrderDishes();
        orderDishes.forEach(orderDish -> {
            Dishes dish = orderDish.getDish();
            Long countPurchases = orderService.countDishPurchases(dish);
            log.info("Dish " + dish.getName() + " has " + countPurchases + " purchases");
            if (updateDish(countPurchases, dish)) {
                dish.setType(DishType.POPULAR);
                dish.setPrice(dish.getPrice() * 1.0573);
                dishesRepository.save(dish);
            }
        });
    }

    private Boolean updateDish(Long countPurchases, Dishes dish) {
        return countPurchases > 100 && dish.getType() == DishType.COMMON;
    }

}
