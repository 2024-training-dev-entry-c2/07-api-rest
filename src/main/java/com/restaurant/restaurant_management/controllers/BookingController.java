package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.dto.BookingRequestDTO;
import com.restaurant.restaurant_management.dto.BookingResponseDTO;
import com.restaurant.restaurant_management.models.Booking;
import com.restaurant.restaurant_management.models.Client;
import com.restaurant.restaurant_management.services.BookingService;
import com.restaurant.restaurant_management.services.ClientService;
import com.restaurant.restaurant_management.utils.DtoBookingConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

  private final BookingService bookingService;
  private final ClientService clientService;

  public BookingController(BookingService bookingService, ClientService clientService) {
    this.clientService = clientService;
    this.bookingService = bookingService;
  }

  @PostMapping
  public ResponseEntity<String> saveBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
    try {
      Client client = clientService.getClient(bookingRequestDTO.getClientId()).orElseThrow();
      Booking booking = DtoBookingConverter.convertToBooking(bookingRequestDTO, client);
      bookingService.saveBooking(booking);
      return ResponseEntity.ok("Reserva creada con éxito");
    }
    catch (RuntimeException e) {
      return ResponseEntity.badRequest().body("No se pudo crear la reserva");
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable Long id) {
    return bookingService.getBooking(id)
      .map(booking -> ResponseEntity.ok(DtoBookingConverter.convertToResponseDTO(booking)))
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<BookingResponseDTO>> getBookingsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    List<Booking> bookings = bookingService.findBookingsByDate(date);
    List<BookingResponseDTO> response = bookings.stream()
      .map(DtoBookingConverter::convertToResponseDTO)
      .toList();
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BookingResponseDTO> updateBooking(@PathVariable Long id, @RequestBody BookingRequestDTO bookingRequestDTO) {
    try {
      Client client = clientService.getClient(bookingRequestDTO.getClientId()).orElseThrow();
      Booking updated = bookingService.updateBooking(id, DtoBookingConverter.convertToBooking(bookingRequestDTO, client));
      return ResponseEntity.ok(DtoBookingConverter.convertToResponseDTO(updated));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBooking(@PathVariable Long id){
    bookingService.deleteBooking(id);
    return ResponseEntity.noContent().build();
  }

}
