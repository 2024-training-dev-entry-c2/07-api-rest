package com.restaurant.restaurant_management.repositories;

import com.restaurant.restaurant_management.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
  Long countByDateAndTime(LocalDate date, LocalTime time);
  List<Booking> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
