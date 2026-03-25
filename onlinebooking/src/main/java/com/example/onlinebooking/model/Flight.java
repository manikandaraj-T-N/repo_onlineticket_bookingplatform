package com.example.onlinebooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String airline;
    private String stop;
    private int price;

    private String departureTime;
    private String arrivalTime;

    @Column(name = "from_place")
    private String fromPlace;

    @Column(name = "to_place")
    private String toPlace;

    @Column(name = "travel_date")
    private String travelDate;

    private String image;

    private String flightNumber;
    private String duration;

    // GETTERS & SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getDepartureSlot() {
    if (departureTime == null) return "";
    if (departureTime.contains("AM")) return "morning";
    String[] parts = departureTime.split(":");
    int hour = Integer.parseInt(parts[0].trim());
    if (hour < 6 || (hour >= 12 && hour < 18)) return "afternoon";
    return "evening";
}

public String getArrivalSlot() {
    if (arrivalTime == null) return "";
    if (arrivalTime.contains("AM")) return "morning";
    String[] parts = arrivalTime.split(":");
    int hour = Integer.parseInt(parts[0].trim());
    if (hour < 6 || (hour >= 12 && hour < 18)) return "afternoon";
    return "evening";
}
    public String getFlightNumber() {
        return flightNumber;
    }

  
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

   
    public String getDuration() {
        return duration;
    }

   
    public void setDuration(String duration) {
        this.duration = duration;
    }

}