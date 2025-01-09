package restaurant_managment.Services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import restaurant_managment.Models.CustomerModel;
import restaurant_managment.Models.ReservationModel;
import restaurant_managment.Repositories.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

  private final ReservationRepository reservationRepository = mock(ReservationRepository.class);
  private final ReservationService reservationService = new ReservationService();

  public ReservationServiceTest() {
    reservationService.reservationRepository = reservationRepository;
  }

  @Test
  @DisplayName("Get all reservations")
  void getAllReservations() {
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation1 = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    ReservationModel reservation2 = new ReservationModel(2L, customer, LocalDateTime.of(2025, 1, 10, 20, 30), 2, "confirmed");
    List<ReservationModel> reservations = List.of(reservation1, reservation2);

    when(reservationRepository.findAll()).thenReturn(reservations);

    List<ReservationModel> result = reservationService.getAllReservations();
    assertEquals(2, result.size());
    assertEquals(reservations, result);

    verify(reservationRepository).findAll();
  }

  @Test
  @DisplayName("Get reservation by ID")
  void getReservationById() {
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");

    when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

    Optional<ReservationModel> result = reservationService.getReservationById(1L);
    assertEquals(Optional.of(reservation), result);

    verify(reservationRepository).findById(anyLong());
  }

  @Test
  @DisplayName("Create reservation")
  void createReservation() {
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");

    when(reservationRepository.save(any(ReservationModel.class))).thenReturn(reservation);

    ReservationModel result = reservationService.createReservation(reservation);
    assertEquals(reservation, result);

    verify(reservationRepository).save(any(ReservationModel.class));
  }

  @Test
  @DisplayName("Update reservation")
  void updateReservation() {
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    ReservationModel updatedReservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 20, 30), 2, "confirmed");

    when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
    when(reservationRepository.save(any(ReservationModel.class))).thenReturn(updatedReservation);

    ReservationModel result = reservationService.updateReservation(1L, updatedReservation);
    assertEquals(updatedReservation, result);

    verify(reservationRepository).findById(anyLong());
    verify(reservationRepository).save(any(ReservationModel.class));
  }

  @Test
  @DisplayName("Update reservation - not found")
  void updateReservationNotFound() {
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    ReservationModel updatedReservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 20, 30), 2, "confirmed");

    when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      reservationService.updateReservation(1L, updatedReservation);
    });

    assertEquals("Reservation not found", exception.getMessage());

    verify(reservationRepository).findById(anyLong());
    verify(reservationRepository, never()).save(any(ReservationModel.class));
  }

  @Test
  @DisplayName("Delete reservation")
  void deleteReservation() {
    doNothing().when(reservationRepository).deleteById(anyLong());

    reservationService.deleteReservation(1L);

    verify(reservationRepository).deleteById(anyLong());
  }
}