package restaurant_managment.Dto.Order;

import lombok.Getter;
import lombok.Setter;
import restaurant_managment.Dto.Dish.DishResponseDTO;

import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {
  private Long id;
  private Long reservationId;
  private List<DishResponseDTO> dishes;
  private String status;
}