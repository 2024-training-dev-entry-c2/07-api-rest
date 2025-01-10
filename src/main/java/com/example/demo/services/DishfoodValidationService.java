package com.example.demo.services;

import com.example.demo.models.Dishfood;
import com.example.demo.models.Order;
import com.example.demo.repositories.DishfoodRepository;
import com.example.demo.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class DishfoodValidationService {
    private final DishfoodRepository dishfoodRepository;
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;

    public DishfoodValidationService(DishfoodRepository dishfoodRepository, OrderRepository orderRepository, NotificationService notificationService) {
        this.dishfoodRepository = dishfoodRepository;
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    public void checkDishFood(Order order) {
        for (Dishfood dish : order.getDishfoods()) {
            long dishCount = orderRepository.countByDishfoods_Id(dish.getId());
            if (dishCount > 11 && !dish.getIsPopular()) {
                dish.setIsPopular(true);
                dishfoodRepository.save(dish);
                notificationService.notifyObserversdish(order);
            }
        }
    }
}
