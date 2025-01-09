package restaurant_managment.Proxy;

import restaurant_managment.Models.ReservationModel;
import restaurant_managment.Models.CustomerModel;
import restaurant_managment.interfaces.IReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceProxyTest {

  @Mock
  private IReservationService reservationService;

  @InjectMocks
  private ReservationServiceProxy reservationServiceProxy;

  private ReservationModel reservation;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    CustomerModel customer = new CustomerModel(1L, false, "John", "Doe", "john.doe@example.com", "1234567890");
    reservation = new ReservationModel(1L, customer, LocalDateTime.now(), 4, "Pending");
  }

  @Test
  void testGetReservationById_ReservationNotInCache() {
    when(reservationService.getReservationById(1L)).thenReturn(Optional.of(reservation));

    Optional<ReservationModel> result = reservationServiceProxy.getReservationById(1L);

    assertTrue(result.isPresent());
    assertEquals(reservation, result.get());

    verify(reservationService, times(1)).getReservationById(1L);
  }

  @Test
  void testGetReservationById_ReservationInCache() {

    when(reservationService.getReservationById(1L)).thenReturn(Optional.of(reservation));
    reservationServiceProxy.getReservationById(1L);

    Optional<ReservationModel> result = reservationServiceProxy.getReservationById(1L);

    assertTrue(result.isPresent());
    assertEquals(reservation, result.get());

    verify(reservationService, times(1)).getReservationById(1L);
  }
}