package restaurant_managment.Dto.Menu;

import lombok.Getter;
import lombok.Setter;
import restaurant_managment.Dto.Dish.DishResponseDTO;

import java.util.List;

@Setter
@Getter
public class MenuResponseDTO {
  private Long id;
  private String name;
  private List<DishResponseDTO> dishes;

}