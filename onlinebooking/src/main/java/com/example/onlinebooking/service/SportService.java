package com.example.onlinebooking.service;

import com.example.onlinebooking.model.Sport;
import com.example.onlinebooking.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;

    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    public Sport getSportById(Long id) {
        return sportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sport not found: " + id));
    }

    public List<Sport> filterSports(Integer maxPrice, String category, String city) {
        return sportRepository.findAll().stream()
                .filter(s -> maxPrice == null || s.getPrice() <= maxPrice)
                .filter(s -> category == null || category.equals("all") || category.equals(s.getCategory()))
                .filter(s -> city == null || city.equals("all") || city.equals(s.getCity()))
                .toList();
    }
}