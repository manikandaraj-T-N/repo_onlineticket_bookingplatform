package com.example.onlinebooking.service;

import com.example.onlinebooking.model.Hotel;
import com.example.onlinebooking.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found: " + id));
    }

    public List<Hotel> filterHotels(
            String area, String category, Integer maxPrice) {
        return hotelRepository.findAll().stream()
                .filter(h -> area == null || area.isEmpty() ||
                        h.getCity().toLowerCase().contains(area.toLowerCase()) ||
                        h.getName().toLowerCase().contains(area.toLowerCase()))
                .filter(h -> category == null || category.equals("all") || category.equals(h.getCategory()))
                .filter(h -> maxPrice == null || h.getPricePerNight() <= maxPrice)
                .toList();
    }
}