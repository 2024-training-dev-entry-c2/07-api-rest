package restaurant_managment.Utils.Dto.Dish;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishRequestDTO {
  private String name;
  private Double price;
  private Boolean isPopular;
  private Boolean isAvailable;
  private String description;
}