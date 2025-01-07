package com.training.restaurant.services.menus;

import com.training.restaurant.dto.menus.CreateMenuDto;
import com.training.restaurant.dto.menus.MenuDto;
import com.training.restaurant.dto.menus.UpdateMenuDto;
import com.training.restaurant.models.Menu;
import com.training.restaurant.repositories.MenuRepository;
import com.training.restaurant.services.interfaces.IMenuService;
import com.training.restaurant.utils.MenuConverter;
import com.training.restaurant.utils.exceptions.BadRequestException;
import com.training.restaurant.utils.exceptions.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository){
        this.menuRepository = menuRepository;
    }
    @Override
    public Menu createMenu(CreateMenuDto createMenuDto) {
        try{
             return menuRepository.save(MenuConverter.toMenu(createMenuDto));
        }catch (DataIntegrityViolationException e){
            String message = "Ocurrio un error al intentar guardar el menu";
            throw new BadRequestException(message);
        }
    }

    @Override
    public List<MenuDto> findAllMenus() {
        return menuRepository.findAll().stream().map(MenuConverter::toMenuDto).toList();
    }

    @Override
    public Menu findMenuById(Long id) {
        return menuRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontro el menu con id "+ id ));
    }

    @Override
    public void removeMenu(Long id) {
        Menu menu = findMenuById(id);
        menuRepository.delete(menu);
    }

    @Override
    public void updateMenu(Long id, UpdateMenuDto updateMenuDto) {
        Menu menu = findMenuById(id);
        updateMenuFields(menu, updateMenuDto);
        menuRepository.save(menu);
    }

    private void updateMenuFields(Menu menu, UpdateMenuDto updateMenuDto){
        updateMenuNameIfPresent(menu, updateMenuDto.name());
        updateMenuScheduleIfPresent(menu, updateMenuDto.schedule());
    }

    private void updateMenuNameIfPresent(Menu menu, String name){
        if(name != null){
            menu.setName(name);
        }
    }

    private void updateMenuScheduleIfPresent(Menu menu, String schedule){
        if(schedule != null){
            menu.setSchedule(schedule);
        }
    }
}
