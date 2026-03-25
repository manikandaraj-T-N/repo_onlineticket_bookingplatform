package com.example.onlinebooking.service;

import com.example.onlinebooking.model.Event;
import com.example.onlinebooking.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found: " + id));
    }

    public List<Event> filterEvents(
            List<String> category, String city, Integer maxPrice) {
        return eventRepository.findAll().stream()
                .filter(e -> category == null || category.isEmpty() || category.contains(e.getCategory()))
                .filter(e -> city == null || city.equals("all") || city.equals(e.getCity()))
                .filter(e -> maxPrice == null || e.getPrice() <= maxPrice)
                .toList();
    }
}