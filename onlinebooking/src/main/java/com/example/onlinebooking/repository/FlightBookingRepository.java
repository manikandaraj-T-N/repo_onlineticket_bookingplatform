package com.example.onlinebooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlinebooking.model.FlightBooking;

public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
    
    List<FlightBooking> findByEmail(String email);
    List<FlightBooking> findByFlightId(Long flightId);
}