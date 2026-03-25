package com.example.onlinebooking.repository;

import com.example.onlinebooking.model.ActivityBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActivityBookingRepository extends JpaRepository<ActivityBooking, Long> {
    List<ActivityBooking> findByEmail(String email);
}