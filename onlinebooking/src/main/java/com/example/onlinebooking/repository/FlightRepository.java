package com.example.onlinebooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlinebooking.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}