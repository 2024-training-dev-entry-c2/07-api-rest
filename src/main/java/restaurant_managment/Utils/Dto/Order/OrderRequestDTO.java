package restaurant_managment.Utils.Dto.Order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
  private Long reservationId;
  private List<Long> dishIds;
  private String status;
}