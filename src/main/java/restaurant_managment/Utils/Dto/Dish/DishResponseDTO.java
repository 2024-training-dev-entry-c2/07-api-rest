package restaurant_managment.Utils.Dto.Dish;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DishResponseDTO {
  private Long id;
  private String name;
  private String description;
  private Double price;

}