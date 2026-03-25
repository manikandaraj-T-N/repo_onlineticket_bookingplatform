package com.example.onlinebooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlinebooking.model.Train;

public interface TrainRepository extends JpaRepository<Train, Long> {
}