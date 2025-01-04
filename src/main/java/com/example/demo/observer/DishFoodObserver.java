package com.example.demo.observer;

import com.example.demo.models.Dishfood;
import com.example.demo.repositories.OrderRepository;

public class DishFoodObserver implements Observer {
    private final Dishfood dishfood;
    private final OrderRepository orderRepository;


    public DishFoodObserver(Dishfood dishfood, OrderRepository orderRepository) {
        this.dishfood = dishfood;
        this.orderRepository = orderRepository;
    }

    @Override
    public void update() {
        long orderTimes = orderRepository.countByDishfoods_Id(dishfood.getId());
        if (orderTimes > 100 && !dishfood.getIsPopular()) {
            dishfood.setIsPopular(true);
            dishfood.setPrice(dishfood.getPrice() * 1.0573); // Aumenta el precio en 5.73%
            System.out.println("El plato " + dishfood.getName() + " ahora es popular.");
        }
    }
}
