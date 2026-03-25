package com.example.onlinebooking.repository;

import com.example.onlinebooking.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
}