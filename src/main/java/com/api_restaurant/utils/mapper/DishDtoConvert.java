package com.api_restaurant.utils.mapper;

import com.api_restaurant.dto.dish.DishRequestDTO;
import com.api_restaurant.dto.dish.DishResponseDTO;
import com.api_restaurant.models.Dish;
import com.api_restaurant.models.Menu;
import com.api_restaurant.repositories.MenuRepository;

public class DishDtoConvert {

    public static DishResponseDTO convertToResponseDto(Dish dish) {
        DishResponseDTO dishResponseDTO = new DishResponseDTO();
        dishResponseDTO.setId(dish.getId());
        dishResponseDTO.setName(dish.getName());
        dishResponseDTO.setDescription(dish.getDescription());
        dishResponseDTO.setPrice(dish.getPrice());
        dishResponseDTO.setSpecialDish(dish.getSpecialDish());

        if (dish.getMenu() != null) {
            DishResponseDTO.MenuSummaryDTO menuSummaryDTO = new DishResponseDTO.MenuSummaryDTO();
            menuSummaryDTO.setId(dish.getMenu().getId());
            menuSummaryDTO.setName(dish.getMenu().getName());
            dishResponseDTO.setMenu(menuSummaryDTO);
        }

        return dishResponseDTO;
    }

    public static Dish convertToEntity(DishRequestDTO dto, MenuRepository menuRepository) {
        Menu menu = dto.getMenuId() != null ? menuRepository.findById(dto.getMenuId()).orElse(null) : null;
        return new Dish(dto.getName(), dto.getDescription(), dto.getPrice(), menu);
    }
}