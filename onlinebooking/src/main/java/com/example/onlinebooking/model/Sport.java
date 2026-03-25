package com.example.onlinebooking.model;

import jakarta.persistence.*;

@Entity
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;   
    private String venue;
    private String city;
    private String eventDate;
    private int price;
    private double rating;
    private String image;

    private String matchTeams;
    private String eventTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

   
    public String getMatchTeams() {
        return matchTeams;
    }

    
    public void setMatchTeams(String matchTeams) {
        this.matchTeams = matchTeams;
    }

    
    public String getEventTime() {
        return eventTime;
    }

   
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

}