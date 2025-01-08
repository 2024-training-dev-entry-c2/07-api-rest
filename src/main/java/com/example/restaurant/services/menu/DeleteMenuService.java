package com.example.restaurant.services.menu;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Menu;
import com.example.restaurant.observers.MenuSubject;
import com.example.restaurant.repositories.IMenuRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteMenuService implements ICommandParametrized<Void, Long> {

    private final IMenuRepository menuRepository;
    private final MenuSubject menuSubject;

    @Autowired
    public DeleteMenuService(IMenuRepository menuRepository, MenuSubject menuSubject) {
        this.menuRepository = menuRepository;
        this.menuSubject = menuSubject;
    }

    @Override
    public Void execute(Long id) {
        Menu menuToDelete = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menú con el id " + id + " no encontrado"));

        menuRepository.deleteById(id);
        menuSubject.notifyObservers(EventType.DELETE, menuToDelete);
        return null;
    }
}
