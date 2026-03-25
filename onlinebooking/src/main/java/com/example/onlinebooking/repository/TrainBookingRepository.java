package com.example.onlinebooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlinebooking.model.TrainBooking;

public interface TrainBookingRepository extends JpaRepository<TrainBooking, Long> {
    List<TrainBooking> findByEmail(String email);
}