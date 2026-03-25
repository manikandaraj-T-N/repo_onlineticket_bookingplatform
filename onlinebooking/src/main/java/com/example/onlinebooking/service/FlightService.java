package com.example.onlinebooking.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onlinebooking.model.Flight;
import com.example.onlinebooking.repository.FlightRepository;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
    }

    public List<Flight> filterFlights(
            Integer minPrice, Integer maxPrice,
            List<String> airline, List<String> stop,
            List<String> departure, List<String> arrival,
            String from, String to, String date) {

        return flightRepository.findAll().stream()
                .filter(f -> minPrice == null || f.getPrice() >= minPrice)
                .filter(f -> maxPrice == null || f.getPrice() <= maxPrice)
                .filter(f -> airline == null || airline.isEmpty() || airline.contains(f.getAirline()))
                .filter(f -> stop == null || stop.isEmpty() || stop.contains(f.getStop()))
                .filter(f -> departure == null || departure.isEmpty() || departure.contains(f.getDepartureSlot()))
                .filter(f -> arrival == null || arrival.isEmpty() || arrival.contains(f.getArrivalSlot()))
                .filter(f -> from == null || from.isEmpty() || f.getFromPlace().toLowerCase().contains(from.toLowerCase()))
                .filter(f -> to == null || to.isEmpty() || f.getToPlace().toLowerCase().contains(to.toLowerCase()))
                .filter(f -> date == null || date.isEmpty() || f.getTravelDate().equals(date))
                .toList();
    }
}