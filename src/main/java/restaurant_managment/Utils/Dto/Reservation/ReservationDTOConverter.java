package restaurant_managment.Utils.Dto.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurant_managment.Models.CustomerModel;
import restaurant_managment.Models.ReservationModel;
import restaurant_managment.Repositories.CustomerRepository;

@Component
public class ReservationDTOConverter {

  @Autowired
  public CustomerRepository customerRepository;

  public ReservationModel toReservation(ReservationRequestDTO dto) {
    ReservationModel reservation = new ReservationModel();
    reservation.setTime(dto.getTime());
    reservation.setPeople(dto.getPeople());
    reservation.setStatus(dto.getStatus());

    CustomerModel customer = customerRepository.findById(dto.getCustomerId())
      .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    reservation.setCustomer(customer);

    return reservation;
  }

  public ReservationResponseDTO toReservationResponseDTO(ReservationModel reservation) {
    ReservationResponseDTO dto = new ReservationResponseDTO();
    dto.setId(reservation.getId());
    dto.setCustomerId(reservation.getCustomer().getId());
    dto.setTime(reservation.getTime());
    dto.setPeople(reservation.getPeople());
    dto.setStatus(reservation.getStatus());
    return dto;
  }
}