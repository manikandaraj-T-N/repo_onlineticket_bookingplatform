package com.example.onlinebooking.repository;

import com.example.onlinebooking.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Long> {
}