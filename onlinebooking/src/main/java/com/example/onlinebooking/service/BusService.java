package com.example.onlinebooking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.onlinebooking.model.Bus;
import com.example.onlinebooking.repository.BusRepository;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public Bus getBusById(Long id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found: " + id));
    }

    public Page<Bus> filterBuses(
            Integer maxPrice, List<String> busType,
            List<String> departure, String from, String to,
            String date, String operator, int page) {

        List<Bus> filtered = busRepository.findAll().stream()
                .filter(b -> maxPrice == null || b.getPrice() <= maxPrice)
                .filter(b -> busType == null || busType.isEmpty() || busType.contains(b.getBusType()))
                .filter(b -> departure == null || departure.isEmpty() || departure.contains(b.getDepartureSlot()))
                .filter(b -> from == null || from.isEmpty() || b.getFromPlace().toLowerCase().contains(from.toLowerCase()))
                .filter(b -> to == null || to.isEmpty() || b.getToPlace().toLowerCase().contains(to.toLowerCase()))
                .filter(b -> date == null || date.isEmpty() || b.getTravelDate().equals(date))
                .filter(b -> operator == null || operator.equals("all") || operator.equals(b.getBusName()))
                .toList();

        // pagination
        int pageSize = 5;
        int start = page * pageSize;
        int end = Math.min(start + pageSize, filtered.size());

        List<Bus> pageContent = (start >= filtered.size()) 
                ? new ArrayList<>() 
                : filtered.subList(start, end);

        return new PageImpl<>(pageContent, PageRequest.of(page, pageSize), filtered.size());
    }
}