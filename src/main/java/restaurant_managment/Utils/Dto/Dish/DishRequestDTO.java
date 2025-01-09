package restaurant_managment.Utils.Dto.Dish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishRequestDTO {
  private String name;
  private Double price;
  private Boolean isPopular;
  private Boolean isAvailable;
  private String description;
}