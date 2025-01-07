package com.restaurante.restaurante.mapper;

import com.restaurante.restaurante.dto.MenuDTO;
import com.restaurante.restaurante.mapper.DishMapper;
import com.restaurante.restaurante.models.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuMapper {
    private final DishMapper dishMapper;

    @Autowired
    public MenuMapper(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }

    public MenuDTO toDTO(Menu menu) {
        if (menu == null) {
            return null;
        }

        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setDescription(menu.getDescription());
        dto.setDishes(dishMapper.toDTOList(menu.getDishes()));

        return dto;
    }

    public static Menu toEntity(MenuDTO dto) {
        if (dto == null) {
            return null;
        }

        Menu menu = new Menu();

        menu.setName(dto.getName());
        menu.setDescription(dto.getDescription());
        // Los platos se deben setear desde el servicio
        return menu;
    }

    public List<MenuDTO> toDTOList(List<Menu> menus) {
        if (menus == null) {
            return Collections.emptyList();
        }
        return menus.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}