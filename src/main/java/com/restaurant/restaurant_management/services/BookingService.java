package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.constants.AppConstants;
import com.restaurant.restaurant_management.models.Booking;
import com.restaurant.restaurant_management.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;

  public BookingService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  public Boolean saveBooking(Booking booking) {
    Long bookingQuantity = bookingRepository.countByDateAndTime(booking.getDate(), booking.getTime());
    if (bookingQuantity < AppConstants.MAX_DAILY_BOOKINGS) {
      bookingRepository.save(booking);
      return true;
    }
    return false;
  }

  public Optional<Booking> getBooking(Long id) {
    return bookingRepository.findById(id);
  }

  public List<Booking> findBookingsByDate(LocalDate specificDate) {
    LocalDateTime startDate = specificDate.atStartOfDay();
    LocalDateTime endDate = specificDate.atTime(LocalTime.MAX);
    return bookingRepository.findByDateBetween(startDate.toLocalDate(), endDate.toLocalDate());
  }

  public Booking updateBooking(Long id, Booking booking) {
    Long bookingQuantity = bookingRepository.countByDateAndTime(booking.getDate(), booking.getTime());
    if(bookingQuantity < AppConstants.MAX_DAILY_BOOKINGS){
      return bookingRepository.findById(id).map(x -> {
        x.setDate(booking.getDate());
        x.setTime(booking.getTime());
        x.setClient(booking.getClient());
        return bookingRepository.save(x);
      }).orElseThrow(() -> new RuntimeException("Reserva con el id " + id + " no pudo ser actualizada"));
    }
     return null;
  }

  public void deleteBooking(Long id) {
    bookingRepository.deleteById(id);
  }

}
