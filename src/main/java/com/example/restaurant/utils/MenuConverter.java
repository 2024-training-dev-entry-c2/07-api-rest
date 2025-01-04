package com.example.restaurant.utils;

import com.example.restaurant.dtos.MenuDTO;
import com.example.restaurant.models.Menu;

public class MenuConverter {
	public static MenuDTO convertEntityToDto(Menu menu) {
		MenuDTO menuDto = new MenuDTO();
		menuDto.setName(menu.getName());
		menuDto.setDescription(menu.getDescription());
		return menuDto;
	}

	public static Menu convertDtoToEntity(MenuDTO menuDto) {
		Menu menu = new Menu();
		menu.setName(menuDto.getName());
		menu.setDescription(menuDto.getDescription());
		return menu;
	}
}