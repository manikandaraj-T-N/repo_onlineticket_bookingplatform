package com.example.onlinebooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onlinebooking.model.Train;
import com.example.onlinebooking.repository.TrainRepository;

@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public Train getTrainById(Long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found: " + id));
    }

    public List<Train> filterTrains(
            Integer maxPrice, List<String> trainType,
            List<String> departure, String from, String to, String date) {

        return trainRepository.findAll().stream()
                .filter(t -> maxPrice == null || t.getPrice() <= maxPrice)
                .filter(t -> trainType == null || trainType.isEmpty() || trainType.contains(t.getTrainType()))
                .filter(t -> departure == null || departure.isEmpty() || departure.contains(t.getDepartureSlot()))
                .filter(t -> from == null || from.isEmpty() || t.getFromPlace().toLowerCase().contains(from.toLowerCase()))
                .filter(t -> to == null || to.isEmpty() || t.getToPlace().toLowerCase().contains(to.toLowerCase()))
                .filter(t -> date == null || date.isEmpty() || t.getTravelDate().equals(date))
                .toList();
    }
}