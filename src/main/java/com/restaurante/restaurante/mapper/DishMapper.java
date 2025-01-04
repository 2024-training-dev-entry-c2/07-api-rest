package com.restaurante.restaurante.mapper;

import com.restaurante.restaurante.dto.DishDTO;
import com.restaurante.restaurante.models.Dish;
import com.restaurante.restaurante.models.Menu;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishMapper {

    public DishDTO toDTO(Dish dish) {
        if (dish == null) {
            return null;
        }

        DishDTO dto = new DishDTO();

        dto.setName(dish.getName());
        dto.setPrice(dish.getPrice());
        dto.setDishType(dish.getDishType());
        // Si el plato pertenece a un menú, solo incluimos el ID del menú
        if (dish.getMenu() != null) {
            dto.setMenuId(dish.getMenu().getId());
        }
        return dto;
    }

    public static Dish toEntity(DishDTO dto) {
        if (dto == null) {
            return null;
        }

        Dish dish = new Dish();

        dish.setName(dto.getName());
        dish.setPrice(dto.getPrice());
        dish.setDishType(dto.getDishType());
        Menu menu = new Menu();
        menu.setId(dto.getMenuId());
        dish.setMenu(menu);
        // El menú se debe setear desde el servicio
        return dish;
    }

    public List<DishDTO> toDTOList(List<Dish> dishes) {
        if (dishes == null) {
            return Collections.emptyList();
        }
        return dishes.stream()

                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

