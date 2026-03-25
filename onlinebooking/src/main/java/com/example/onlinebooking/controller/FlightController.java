package com.example.onlinebooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onlinebooking.model.Flight;
import com.example.onlinebooking.service.FlightService;

@Controller
public class FlightController {

    @Autowired
    private FlightService flightService;  

    @GetMapping("/homeflight")
    public String home(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        return "homeflight";
    }

    @GetMapping("/flights")
    public String getFlights(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) List<String> airline,
            @RequestParam(required = false) List<String> stop,
            @RequestParam(required = false) List<String> departure,
            @RequestParam(required = false) List<String> arrival,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String date,
            Model model) {

        List<Flight> filtered = flightService.filterFlights(
                minPrice, maxPrice, airline, stop,
                departure, arrival, from, to, date);

        model.addAttribute("flights", filtered);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("date", date);

        return "homeflight";
    }

   
}