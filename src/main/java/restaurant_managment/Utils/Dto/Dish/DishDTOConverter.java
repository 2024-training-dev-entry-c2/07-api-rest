package restaurant_managment.Utils.Dto.Dish;

import org.springframework.stereotype.Component;
import restaurant_managment.Models.DishModel;

@Component
public class DishDTOConverter {

  public static DishModel toDish(DishRequestDTO dto) {
    DishModel dish = new DishModel();
    dish.setName(dto.getName());
    dish.setPrice(dto.getPrice());
    dish.setIsPopular(dto.getIsPopular());
    dish.setIsAvailable(dto.getIsAvailable());
    dish.setDescription(dto.getDescription());
    return dish;
  }

  public DishResponseDTO toDishResponseDTO(DishModel dish) {
    DishResponseDTO dto = new DishResponseDTO();
    dto.setId(dish.getId());
    dto.setName(dish.getName());
    dto.setPrice(dish.getPrice());
    return dto;
  }
}