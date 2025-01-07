package com.training.restaurant.utils;

import com.training.restaurant.dto.menus.CreateMenuDto;
import com.training.restaurant.dto.menus.MenuDto;
import com.training.restaurant.models.Menu;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class MenuConverter {

    public static MenuDto toMenuDto(Menu menu){
        return new MenuDto(menu);
    }

    public static Menu toMenu(CreateMenuDto createMenuDto){
        return new Menu(
                createMenuDto.name(),
                createMenuDto.schedule(),
                ZonedDateTime.now(ZoneId.of("America/Caracas"))
        );
    }
}
