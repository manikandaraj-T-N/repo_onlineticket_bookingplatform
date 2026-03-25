package com.example.onlinebooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlinebooking.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}