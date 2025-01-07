package com.example.demo.DTO.converterDTO;

import com.example.demo.DTO.menu.MenuRequestDTO;
import com.example.demo.DTO.menu.MenuResponseDTO;
import com.example.demo.models.Menu;

import java.util.Collections;
import java.util.stream.Collectors;

public class MenuConverter {

    public static Menu toEntity(MenuRequestDTO dto) {
        return Menu.builder()
                .name(dto.getName())
                .build();
    }

    public static MenuResponseDTO toResponseDTO(Menu menu) {
        return MenuResponseDTO.builder()
                .id(menu.getId())
                .name(menu.getName())
                .dishfoods(menu.getDishfoods() == null ? Collections.emptyList() : menu.getDishfoods()
                        .stream()
                        .map(DishfoodConverter::toResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
