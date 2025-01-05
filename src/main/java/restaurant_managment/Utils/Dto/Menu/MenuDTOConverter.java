package restaurant_managment.Utils.Dto.Menu;

import restaurant_managment.Repositories.DishRepository;
import restaurant_managment.Utils.Dto.Dish.DishResponseDTO;
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

  @Autowired
  private DishRepository dishRepository;

  public MenuModel toMenu(MenuRequestDTO dto) {
    MenuModel menu = new MenuModel();
    menu.setName(dto.getName());

    List<DishModel> dishes = dishRepository.findAllById(dto.getDishIds());
    if (dishes.size() != dto.getDishIds().size()) {
      throw new IllegalArgumentException("One or more dishes not found");
    }
    menu.setDishes(dishes);

    return menu;
  }

  public MenuResponseDTO toMenuResponseDTO(MenuModel menu) {
    MenuResponseDTO dto = new MenuResponseDTO();
    dto.setId(menu.getId());
    dto.setName(menu.getName());

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