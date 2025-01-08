package com.example.restaurant.services.dish;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Dish;
import com.example.restaurant.observers.DishSubject;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteDishService implements ICommandParametrized<Void, Long> {

    private final IDishRepository dishRepository;
    private final DishSubject dishSubject;

    @Autowired
    public DeleteDishService(IDishRepository dishRepository, DishSubject dishSubject) {
        this.dishRepository = dishRepository;
        this.dishSubject = dishSubject;
    }

    @Override
    public Void execute(Long id) {
        Dish dishToDelete = dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato con el id " + id + " no encontrado"));

        dishRepository.deleteById(id);
        dishSubject.notifyObservers(EventType.DELETE, dishToDelete);
        return null;
    }
}
