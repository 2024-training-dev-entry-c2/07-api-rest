package restaurant_managment.Utils.Dto.Reservation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponseDTO {
  private Long id;
  private Long customerId;
  private LocalDateTime time;
  private Integer people;
  private String status;
}