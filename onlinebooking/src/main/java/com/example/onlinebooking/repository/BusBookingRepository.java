package com.example.onlinebooking.repository;

import com.example.onlinebooking.model.BusBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BusBookingRepository extends JpaRepository<BusBooking, Long> {
    List<BusBooking> findByEmail(String email);
    List<BusBooking> findByBusId(Long busId);

  
}