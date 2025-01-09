package restaurant_managment.Utils.Dto.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant_managment.Utils.Dto.Dish.DishResponseDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
  private Long id;
  private Long reservationId;
  private List<DishResponseDTO> dishes;
  private String status;
  private Double totalPrice;
}