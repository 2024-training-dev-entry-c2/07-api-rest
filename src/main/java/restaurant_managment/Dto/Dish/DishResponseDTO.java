package restaurant_managment.Dto.Dish;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DishResponseDTO {
  private Long id;
  private String name;
  private Double price;

}