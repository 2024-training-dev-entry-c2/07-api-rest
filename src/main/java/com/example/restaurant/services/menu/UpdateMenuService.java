package com.example.restaurant.services.menu;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.models.Menu;
import com.example.restaurant.observers.MenuSubject;
import com.example.restaurant.repositories.IMenuRepository;
import com.example.restaurant.services.interfaces.ICommandModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateMenuService implements ICommandModifier<Menu, Menu> {

    private final IMenuRepository menuRepository;
    private final MenuSubject menuSubject;

    @Autowired
    public UpdateMenuService(IMenuRepository menuRepository, MenuSubject menuSubject) {
        this.menuRepository = menuRepository;
        this.menuSubject = menuSubject;
    }

    @Override
    public Menu execute(Long id, Menu updatedMenu) {
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menú con el id " + id + " no encontrado"));

        existingMenu.setName(updatedMenu.getName());
        existingMenu.setDescription(updatedMenu.getDescription());

        Menu savedMenu = menuRepository.save(existingMenu);
        menuSubject.notifyObservers(EventType.UPDATE, savedMenu);

        return savedMenu;
    }
}
