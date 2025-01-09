package restaurant_managment.Utils.Dto.Dish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DishResponseDTO {
  private Long id;
  private String name;
  private String description;
  private Boolean isPopular;
  private Double price;

}