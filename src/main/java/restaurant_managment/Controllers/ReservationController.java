package restaurant_managment.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant_managment.Proxy.ReservationServiceProxy;
import restaurant_managment.Utils.Dto.Reservation.ReservationRequestDTO;
import restaurant_managment.Utils.Dto.Reservation.ReservationResponseDTO;
import restaurant_managment.Utils.Dto.Reservation.ReservationDTOConverter;
import restaurant_managment.Models.ReservationModel;
import restaurant_managment.Services.ReservationService;
import restaurant_managment.interfaces.IReservationService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationServiceProxy reservationServiceProxy;

  @Autowired
  private ReservationDTOConverter reservationDTOConverter;

  @PostMapping
  public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
    ReservationModel reservationModel = reservationDTOConverter.toReservation(reservationRequestDTO);
    ReservationModel createdReservation = reservationService.createReservation(reservationModel);
    ReservationResponseDTO responseDTO = reservationDTOConverter.toReservationResponseDTO(createdReservation);
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable Long id) {
    Optional<ReservationModel> reservationModel = reservationServiceProxy.getReservationById(id);
    return reservationModel.map(reservation -> ResponseEntity.ok(reservationDTOConverter.toReservationResponseDTO(reservation)))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
    List<ReservationModel> reservations = reservationService.getAllReservations();
    List<ReservationResponseDTO> responseDTOs = reservations.stream()
      .map(reservationDTOConverter::toReservationResponseDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(responseDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ReservationResponseDTO> updateReservation(@PathVariable Long id, @RequestBody ReservationRequestDTO reservationRequestDTO) {
    ReservationModel updatedReservationModel = reservationDTOConverter.toReservation(reservationRequestDTO);
    ReservationModel updatedReservation = reservationService.updateReservation(id, updatedReservationModel);
    ReservationResponseDTO responseDTO = reservationDTOConverter.toReservationResponseDTO(updatedReservation);
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
    reservationService.deleteReservation(id);
    return ResponseEntity.noContent().build();
  }
}