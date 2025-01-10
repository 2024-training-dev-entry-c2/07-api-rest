package com.example.demo.services;

import com.example.demo.models.Dishfood;
import com.example.demo.models.Order;
import com.example.demo.observer.FrequentClientObserver;
import com.example.demo.observer.PopularDishObserver;
import com.example.demo.observer.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final Subject subject;

    @Autowired
    public NotificationService(FrequentClientObserver frequentClientObserver, PopularDishObserver popularDishObserver) {
        this.subject = new Subject();
        this.subject.addObserver(frequentClientObserver);
        this.subject.addObserver(popularDishObserver);
    }

    public void notifyObservers(Order order) {
        if (order.getClient().getIsOften()) {
            subject.notifyObservers("El cliente " + order.getClient().getName() + " ahora es un cliente frecuente.");
        }

    }
    public void notifyObserversdish(Order order) {
        order.getDishfoods().stream()
                .filter(Dishfood::getIsPopular)
                .forEach(dish -> subject.notifyObservers("El plato " + dish.getName() + " ahora es popular."));
    }
}
