package com.example.restaurant.services.menu;

import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IMenuRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetMenuByIdService implements ICommandParametrized<Menu, Long> {

    private final IMenuRepository menuRepository;

    @Autowired
    public GetMenuByIdService(IMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu execute(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menú con el id " + id + " no encontrado"));
    }
}
