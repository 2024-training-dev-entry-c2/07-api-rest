package com.restaurant.restaurant_management.utils;

import com.restaurant.restaurant_management.dto.BookingRequestDTO;
import com.restaurant.restaurant_management.dto.BookingResponseDTO;
import com.restaurant.restaurant_management.models.Booking;
import com.restaurant.restaurant_management.models.Client;

public class DtoBookingConverter {

  public static Booking convertToBooking(BookingRequestDTO bookingRequestDTO, Client client) {
    Booking booking = new Booking();
    booking.setDate(bookingRequestDTO.getDate());
    booking.setTime(bookingRequestDTO.getTime());
    booking.setClient(client);
    return booking;
  }

  public static BookingResponseDTO convertToResponseDTO(Booking booking) {
    BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
    bookingResponseDTO.setId(booking.getId());
    bookingResponseDTO.setDate(booking.getDate());
    bookingResponseDTO.setTime(booking.getTime());
    bookingResponseDTO.setClient(booking.getClient());
    return bookingResponseDTO;
  }
}
