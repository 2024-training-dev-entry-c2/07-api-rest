package com.restaurant.restaurant.services;

import com.restaurant.restaurant.dtos.DishDTO;
import com.restaurant.restaurant.dtos.MenuDTO;
import com.restaurant.restaurant.models.DishModel;
import com.restaurant.restaurant.models.MenuModel;
import com.restaurant.restaurant.repositories.DishRepository;
import com.restaurant.restaurant.repositories.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
  private final MenuRepository menuRepository;
  private final DishRepository dishRepository;
  
  @Transactional
  public List<MenuDTO> findAll(){
    return menuRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Transactional
  public MenuDTO findById(Long id){
    return menuRepository.findById(id).map(this::convertToDto).orElseThrow(() -> new RuntimeException("Menu not found with id " + id));
  }

  @Transactional
  public MenuDTO createMenu(MenuDTO menuDTO){
    MenuModel menuModel = new MenuModel();
    menuModel.setName(menuDTO.getName());
    menuModel.setDescription(menuDTO.getDescription());

    List<DishModel> dishModels = getDishesValidates(menuDTO);
    menuModel.setDishes(dishModels);

    return convertToDto(menuRepository.save(menuModel));
  }

  @Transactional
  public MenuDTO updateMenu(Long id, MenuDTO menuDTO){
    MenuModel menuModel = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found with id " + id));
    menuModel.setName(menuDTO.getName());
    menuModel.setDescription(menuDTO.getDescription());

    List<DishModel> dishModels = getDishesValidates(menuDTO);
    menuModel.setDishes(dishModels);

    return convertToDto(menuRepository.save(menuModel));
  }

  @Transactional
  public void deleteMenu(Long id){
    if(!menuRepository.existsById(id)){
      throw new RuntimeException("Menu not found with id " + id);
    }
    menuRepository.deleteById(id);
  }

  private List<DishModel> getDishesValidates(MenuDTO menuDTO) {
    return menuDTO.getDishes().stream().map(dishDTO -> dishRepository.findById(dishDTO.getId()).orElseThrow(() -> new RuntimeException("Dish not found with id " + dishDTO.getId()))).collect(Collectors.toList());
  }

  private MenuDTO convertToDto(MenuModel menuModel) {
    MenuDTO dto = new MenuDTO();
    dto.setId(menuModel.getId());
    dto.setName(menuModel.getName());
    dto.setDescription(menuModel.getDescription());
    dto.setDishes(menuModel.getDishes().stream().map(this::convertDishToDto).collect(Collectors.toList()));
    return dto;
  }

  private DishDTO convertDishToDto(DishModel dishModel) {
    DishDTO dto = new DishDTO();
    dto.setId(dishModel.getId());
    dto.setName(dishModel.getName());
    dto.setPrice(dishModel.getPrice());
    dto.setType(dishModel.getType());
    return dto;
  }
}
