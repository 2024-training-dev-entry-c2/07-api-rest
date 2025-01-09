package restaurant_managment.Controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import restaurant_managment.Models.CustomerModel;
import restaurant_managment.Models.ReservationModel;
import restaurant_managment.Proxy.ReservationServiceProxy;
import restaurant_managment.Repositories.CustomerRepository;
import restaurant_managment.Services.ReservationService;
import restaurant_managment.Utils.Dto.Reservation.ReservationDTOConverter;
import restaurant_managment.Utils.Dto.Reservation.ReservationRequestDTO;
import restaurant_managment.Utils.Dto.Reservation.ReservationResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

  private final WebTestClient webTestClient;
  private final ReservationServiceProxy reservationServiceProxy;
  private final ReservationService reservationService;
  private final ReservationDTOConverter reservationDTOConverter;
  private final CustomerRepository customerRepository;

  ReservationControllerTest() {
    customerRepository = mock(CustomerRepository.class);
    reservationServiceProxy = mock(ReservationServiceProxy.class);
    reservationService = mock(ReservationService.class);
    reservationDTOConverter = new ReservationDTOConverter();
    reservationDTOConverter.customerRepository = customerRepository;
    webTestClient = WebTestClient.bindToController(new ReservationController(reservationService, reservationServiceProxy, reservationDTOConverter)).build();
  }

  @Test
  @DisplayName("Create reservation")
  void createReservation() {
    CustomerModel customer = new CustomerModel(1L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890");
    ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
    reservationRequestDTO.setCustomerId(1L);
    reservationRequestDTO.setTime(LocalDateTime.of(2025, 1, 10, 19, 30));
    reservationRequestDTO.setPeople(4);
    reservationRequestDTO.setStatus("pending");

    ReservationModel reservation = new ReservationModel(1L, customer, reservationRequestDTO.getTime(), reservationRequestDTO.getPeople(), reservationRequestDTO.getStatus());

    when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
    when(reservationService.createReservation(any(ReservationModel.class))).thenReturn(reservation);

    webTestClient.post()
      .uri("/reservations")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(reservationRequestDTO)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(ReservationResponseDTO.class)
      .value(reservationResponse -> {
        assertEquals(reservation.getId(), reservationResponse.getId());
        assertEquals(reservation.getCustomer().getId(), reservationResponse.getCustomerId());
        assertEquals(reservation.getTime(), reservationResponse.getTime());
        assertEquals(reservation.getPeople(), reservationResponse.getPeople());
        assertEquals(reservation.getStatus(), reservationResponse.getStatus());
      });

    verify(customerRepository).findById(anyLong());
    verify(reservationService).createReservation(any(ReservationModel.class));
  }

  @Test
  @DisplayName("Get reservation by ID")
  void getReservationById() {
    CustomerModel customer = new CustomerModel(1L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890");
    ReservationModel reservation = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");

    when(reservationServiceProxy.getReservationById(anyLong())).thenReturn(Optional.of(reservation));

    webTestClient.get()
      .uri("/reservations/1")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(ReservationResponseDTO.class)
      .value(reservationResponse -> {
        assertEquals(reservation.getId(), reservationResponse.getId());
        assertEquals(reservation.getCustomer().getId(), reservationResponse.getCustomerId());
        assertEquals(reservation.getTime(), reservationResponse.getTime());
        assertEquals(reservation.getPeople(), reservationResponse.getPeople());
        assertEquals(reservation.getStatus(), reservationResponse.getStatus());
      });

    verify(reservationServiceProxy).getReservationById(anyLong());
  }

  @Test
  @DisplayName("Get all reservations")
  void getAllReservations() {
    CustomerModel customer = new CustomerModel(1L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890");
    ReservationModel reservation1 = new ReservationModel(1L, customer, LocalDateTime.of(2025, 1, 10, 19, 30), 4, "pending");
    ReservationModel reservation2 = new ReservationModel(2L, customer, LocalDateTime.of(2025, 1, 11, 20, 30), 2, "confirmed");
    List<ReservationModel> reservations = List.of(reservation1, reservation2);

    when(reservationService.getAllReservations()).thenReturn(reservations);

    webTestClient.get()
      .uri("/reservations")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(ReservationResponseDTO.class)
      .value(reservationResponses -> {
        assertEquals(2, reservationResponses.size());
        assertEquals(reservation1.getId(), reservationResponses.get(0).getId());
        assertEquals(reservation2.getId(), reservationResponses.get(1).getId());
      });

    verify(reservationService).getAllReservations();
  }

  @Test
  @DisplayName("Update reservation")
  void updateReservation() {
    CustomerModel customer = new CustomerModel(1L, false, "Sergio", "Fern", "bYB4B@example.com", "1234567890");
    ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
    reservationRequestDTO.setCustomerId(1L);
    reservationRequestDTO.setTime(LocalDateTime.of(2025, 1, 10, 19, 30));
    reservationRequestDTO.setPeople(4);
    reservationRequestDTO.setStatus("confirmed");

    ReservationModel updatedReservation = new ReservationModel(1L, customer, reservationRequestDTO.getTime(), reservationRequestDTO.getPeople(), reservationRequestDTO.getStatus());

    when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
    when(reservationService.updateReservation(anyLong(), any(ReservationModel.class))).thenReturn(updatedReservation);

    webTestClient.put()
      .uri("/reservations/1")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(reservationRequestDTO)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody(ReservationResponseDTO.class)
      .value(reservationResponse -> {
        assertEquals(updatedReservation.getId(), reservationResponse.getId());
        assertEquals(updatedReservation.getCustomer().getId(), reservationResponse.getCustomerId());
        assertEquals(updatedReservation.getTime(), reservationResponse.getTime());
        assertEquals(updatedReservation.getPeople(), reservationResponse.getPeople());
        assertEquals(updatedReservation.getStatus(), reservationResponse.getStatus());
      });

    verify(customerRepository).findById(anyLong());
    verify(reservationService).updateReservation(anyLong(), any(ReservationModel.class));
  }

  @Test
  @DisplayName("Delete reservation")
  void deleteReservation() {
    webTestClient.delete()
      .uri("/reservations/1")
      .exchange()
      .expectStatus().isNoContent();

    verify(reservationService).deleteReservation(anyLong());
  }
}