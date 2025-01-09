package restaurant_managment.Utils.Dto.Menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant_managment.Utils.Dto.Dish.DishResponseDTO;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDTO {
  private Long id;
  private String name;
  private List<DishResponseDTO> dishes;

}