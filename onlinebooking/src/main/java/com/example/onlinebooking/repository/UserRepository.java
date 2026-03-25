package com.example.onlinebooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlinebooking.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
}