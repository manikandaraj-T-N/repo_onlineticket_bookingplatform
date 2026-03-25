package com.example.onlinebooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busName;
    private String busNumber;
    private String fromPlace;
    private String toPlace;
    private String departureTime;
    private String arrivalTime;
    private String travelDate;
    private String duration;
    private String busType;     // AC, Non-AC, Sleeper, Semi-Sleeper
    private int price;
    private String image;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBusName() { return busName; }
    public void setBusName(String busName) { this.busName = busName; }

    public String getBusNumber() { return busNumber; }
    public void setBusNumber(String busNumber) { this.busNumber = busNumber; }

    public String getFromPlace() { return fromPlace; }
    public void setFromPlace(String fromPlace) { this.fromPlace = fromPlace; }

    public String getToPlace() { return toPlace; }
    public void setToPlace(String toPlace) { this.toPlace = toPlace; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getTravelDate() { return travelDate; }
    public void setTravelDate(String travelDate) { this.travelDate = travelDate; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getBusType() { return busType; }
    public void setBusType(String busType) { this.busType = busType; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDepartureSlot() {
        if (departureTime == null) return "";
        if (departureTime.contains("AM")) return "morning";
        String[] parts = departureTime.split(":");
        int hour = Integer.parseInt(parts[0].trim());
        if (hour >= 12 && hour < 18) return "afternoon";
        return "evening";
    }
}