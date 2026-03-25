package com.example.onlinebooking.repository;

import com.example.onlinebooking.model.EventBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventBookingRepository extends JpaRepository<EventBooking, Long> {
    List<EventBooking> findByEmail(String email);
}