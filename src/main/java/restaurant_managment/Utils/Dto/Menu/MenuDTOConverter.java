package restaurant_managment.Utils.Dto.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.MenuModel;
import restaurant_managment.Repositories.DishRepository;
import restaurant_managment.Utils.Dto.Dish.DishResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuDTOConverter {

  private static DishRepository dishRepository;

  @Autowired
  public MenuDTOConverter(DishRepository dishRepository) {
    this.dishRepository = dishRepository;
  }

  public static MenuModel toMenu(MenuRequestDTO dto) {
    MenuModel menu = new MenuModel();
    menu.setName(dto.getName());

    List<DishModel> dishes = dishRepository.findAllById(dto.getDishIds());
    if (dishes.size() != dto.getDishIds().size()) {
      throw new IllegalArgumentException("One or more dishes not found");
    }
    menu.setDishes(dishes);
    return menu;
  }

  public static MenuResponseDTO toMenuResponseDTO(MenuModel menu) {
    MenuResponseDTO dto = new MenuResponseDTO();
    dto.setId(menu.getId());
    dto.setName(menu.getName());

    List<DishResponseDTO> dishResponses = menu.getDishes().stream()
      .map(dish -> {
        DishResponseDTO dishResponse = new DishResponseDTO();
        dishResponse.setId(dish.getId());
        dishResponse.setName(dish.getName());
        dishResponse.setDescription(dish.getDescription());
        dishResponse.setPrice(dish.getPrice());
        return dishResponse;
      })
      .collect(Collectors.toList());
    dto.setDishes(dishResponses);

    return dto;
  }
}