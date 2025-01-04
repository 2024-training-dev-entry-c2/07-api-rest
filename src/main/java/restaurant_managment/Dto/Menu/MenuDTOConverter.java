package restaurant_managment.Dto.Menu;

import restaurant_managment.Dto.Dish.DishResponseDTO;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.MenuModel;
import restaurant_managment.Repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuDTOConverter {

  @Autowired
  private MenuRepository menuRepository;

  public MenuModel toMenu(MenuRequestDTO dto) {
    MenuModel menu = new MenuModel();
    menu.setName(dto.getName());

    // Convertir los IDs de los platos a objetos DishModel
    List<DishModel> dishes = dto.getDishIds().stream()
      .map(dishId -> menuRepository.getReferenceById(dishId).getDishes().stream()
        .filter(dish -> dish.getId().equals(dishId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Dish not found in Menu")))
      .collect(Collectors.toList());
    menu.setDishes(dishes);

    return menu;
  }

  public MenuResponseDTO toMenuResponseDTO(MenuModel menu) {
    MenuResponseDTO dto = new MenuResponseDTO();
    dto.setId(menu.getId());
    dto.setName(menu.getName());

    // Convertir los objetos DishModel a DishResponseDTO
    List<DishResponseDTO> dishResponses = menu.getDishes().stream()
      .map(dish -> {
        DishResponseDTO dishResponse = new DishResponseDTO();
        dishResponse.setId(dish.getId());
        dishResponse.setName(dish.getName());
        dishResponse.setPrice(dish.getPrice());
        return dishResponse;
      })
      .collect(Collectors.toList());
    dto.setDishes(dishResponses);

    return dto;
  }
}