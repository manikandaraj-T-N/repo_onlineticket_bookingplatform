package com.example.onlinebooking.repository;

import com.example.onlinebooking.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}