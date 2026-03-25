package com.example.onlinebooking.repository;

import com.example.onlinebooking.model.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {
    List<HotelBooking> findByEmail(String email);
}