package com.example.onlinebooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onlinebooking.model.Activity;
import com.example.onlinebooking.repository.ActivityRepository;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public List<Activity> filterActivities(Integer maxPrice, String category, String city) {
        return activityRepository.findAll().stream()
                .filter(a -> maxPrice == null || a.getPrice() <= maxPrice)
                .filter(a -> category == null || category.equals("all") || category.equals(a.getCategory()))
                .filter(a -> city == null || city.equals("all") || city.equals(a.getCity()))
                .toList();
    }

    public Activity getActivityById(Long id) {
    return activityRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Activity not found: " + id));
}
}