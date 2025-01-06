package com.training.restaurant.services.interfaces;

import com.training.restaurant.dto.CreateDishDto;
import com.training.restaurant.dto.CreateMenuDto;
import com.training.restaurant.models.Dishes;
import com.training.restaurant.models.Menu;

import java.util.List;

public interface IMenuDishService {
    Dishes createDishForExistingMenu(Long menuId, CreateDishDto createDishDto);
    Menu createMenuWithDishes(CreateMenuDto createMenuDto);
    List<Dishes> createDishesFromMenu(Menu menu, List<CreateDishDto> createDishDto);
}
