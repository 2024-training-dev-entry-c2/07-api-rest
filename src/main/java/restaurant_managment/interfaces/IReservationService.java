package restaurant_managment.interfaces;

import restaurant_managment.Models.ReservationModel;

import java.util.List;
import java.util.Optional;

public interface IReservationService {
  List<ReservationModel> getAllReservations();
  Optional<ReservationModel> getReservationById(Long id);
}