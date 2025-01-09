package restaurant_managment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant_managment.Models.ReservationModel;
import restaurant_managment.Repositories.ReservationRepository;
import restaurant_managment.interfaces.IReservationService;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IReservationService {

  @Autowired
  ReservationRepository reservationRepository;

  public List<ReservationModel> getAllReservations() {
    return reservationRepository.findAll();
  }

  @Override
  public Optional<ReservationModel> getReservationById(Long id) {
    return reservationRepository.findById(id);
  }

  public ReservationModel createReservation(ReservationModel reservation) {
    return reservationRepository.save(reservation);
  }

  public ReservationModel updateReservation(Long id, ReservationModel updatedReservation) {
    return reservationRepository.findById(id)
      .map(reservation -> {
        reservation.setCustomer(updatedReservation.getCustomer());
        reservation.setTime(updatedReservation.getTime());
        reservation.setPeople(updatedReservation.getPeople());
        reservation.setStatus(updatedReservation.getStatus());
        return reservationRepository.save(reservation);
      })
      .orElseThrow(() -> new RuntimeException("Reservation not found"));
  }

  public void deleteReservation(Long id) {
    reservationRepository.deleteById(id);
  }
}