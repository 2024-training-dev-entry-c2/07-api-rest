package com.training.restaurant.services.menus;

import com.training.restaurant.dto.dishes.CreateDishDto;
import com.training.restaurant.dto.menus.CreateMenuDto;
import com.training.restaurant.models.Dishes;
import com.training.restaurant.models.Menu;
import com.training.restaurant.services.dishes.DishServiceImpl;
import com.training.restaurant.services.interfaces.IMenuDishService;
import com.training.restaurant.utils.DishesConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuDishServiceImpl implements IMenuDishService {

    private final DishServiceImpl dishService;
    private final MenuServiceImpl menuService;

    public MenuDishServiceImpl(DishServiceImpl dishService, MenuServiceImpl menuService) {
        this.dishService = dishService;
        this.menuService = menuService;
    }


    @Override
    public Dishes createDishForExistingMenu(Long menuId, CreateDishDto createDishDto) {
        Dishes dish = DishesConverter.toCreateDish(createDishDto);
        dish.setMenu(menuService.findMenuById(menuId));
        return dishService.createDish(dish);

    }

    @Override
    public Menu createMenuWithDishes(CreateMenuDto createMenuDto) {
        Menu menu = menuService.createMenu(createMenuDto);
        menu.setDishes(createDishesFromMenu(menu, createMenuDto.dishes()));
        return menu;
    }

    @Override
    public List<Dishes> createDishesFromMenu(Menu menu, List<CreateDishDto> createDishDto) {
            return dishService.createAllDishes(DishesConverter.toDishesListWithMenu(menu, createDishDto));
    }

}
