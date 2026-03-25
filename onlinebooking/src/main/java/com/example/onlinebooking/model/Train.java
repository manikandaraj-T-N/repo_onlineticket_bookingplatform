package com.example.onlinebooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainName;
    private String trainNumber;
    private String fromPlace;
    private String toPlace;
    private String departureTime;
    private String arrivalTime;
    private String travelDate;
    private String duration;
    private String trainType;    // Express, Superfast, Rajdhani etc.
    private int price;
    private String image;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

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

    public String getTrainType() { return trainType; }
    public void setTrainType(String trainType) { this.trainType = trainType; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    // departure slot for filter
    public String getDepartureSlot() {
        if (departureTime == null) return "";
        if (departureTime.contains("AM")) return "morning";
        String[] parts = departureTime.split(":");
        int hour = Integer.parseInt(parts[0].trim());
        if (hour >= 12 && hour < 18) return "afternoon";
        return "evening";
    }
}