package restaurant_managment.Proxy;

import restaurant_managment.Models.ReservationModel;
import restaurant_managment.interfaces.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ReservationServiceProxy implements IReservationService {

  @Autowired
  private IReservationService reservationService;

  private Map<Long, ReservationModel> reservationCache = new HashMap<>();
  private List<ReservationModel> allReservationsCache;

  @Override
  public List<ReservationModel> getAllReservations() {
    if (allReservationsCache == null) {
      allReservationsCache = reservationService.getAllReservations();
    }
    return allReservationsCache;
  }

  @Override
  public Optional<ReservationModel> getReservationById(Long id) {
    if (!reservationCache.containsKey(id)) {
      Optional<ReservationModel> reservation = reservationService.getReservationById(id);
      reservation.ifPresent(value -> reservationCache.put(id, value));
    }
    return Optional.ofNullable(reservationCache.get(id));
  }
}