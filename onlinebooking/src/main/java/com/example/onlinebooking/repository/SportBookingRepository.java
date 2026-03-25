package com.example.onlinebooking.repository;

import com.example.onlinebooking.model.SportBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SportBookingRepository extends JpaRepository<SportBooking, Long> {
    List<SportBooking> findByEmail(String email);
}