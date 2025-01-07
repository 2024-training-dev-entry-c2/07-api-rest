package com.restaurante.restaurante.services.impl;

import com.restaurante.restaurante.dto.MenuDTO;
import com.restaurante.restaurante.mapper.MenuMapper;
import com.restaurante.restaurante.models.Menu;
import com.restaurante.restaurante.repositories.MenuRepository;
import com.restaurante.restaurante.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service

public class MenuServiceImpl implements IMenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    public void addMenu(MenuDTO menuDTO) {
        Menu menu = menuMapper.toEntity(menuDTO); // Mapeo del DTO a la entidad
        menuRepository.save(menu); // Guardamos el menú en el repositorio
    }

    @Override
    public Optional<MenuDTO> getMenu(Long id) {
        return menuRepository.findById(id) // Buscamos el menú por id
                .map(menuMapper::toDTO); // Convertimos la entidad a DTO si existe, devolvemos el DTO
    }

    @Override
    public List<MenuDTO> getMenus() {
        return menuRepository.findAll() // Obtenemos todos los menús
                .stream()
                .map(menuMapper::toDTO) // Convertimos cada entidad a DTO
                .toList();
    }

    @Override
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id); // Eliminamos el menú por su ID
    }

    @Override
    public MenuDTO updateMenu(Long id, MenuDTO menuDTO) {
        return menuRepository.findById(id) // Buscamos el menú por ID
                .map(menu -> {
                    // Actualizamos los valores del menú con los datos del DTO
                    menu.setName(menuDTO.getName());
                    menu.setDescription(menuDTO.getDescription());
//                    menu.setDishes(menuDTO.getDishes()); // Establecemos los platos asociados
                    Menu updatedMenu = menuRepository.save(menu); // Guardamos el menú actualizado
                    return menuMapper.toDTO(updatedMenu); // Retornamos el DTO del menú actualizado
                })
                .orElseThrow(() -> new RuntimeException("El menú con el id " + id + " no pudo ser actualizado"));
    }
}