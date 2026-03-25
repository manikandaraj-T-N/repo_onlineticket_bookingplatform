package com.example.onlinebooking.repository;

import com.example.onlinebooking.model.MovieBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieBookingRepository extends JpaRepository<MovieBooking, Long> {
    List<MovieBooking> findByEmail(String email);
}