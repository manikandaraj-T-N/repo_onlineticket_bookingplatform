package com.example.onlinebooking.model;

import jakarta.persistence.*;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;    // Beach Vacations, Weekend Gateways, Mountain Calling, Stay Like Royals, Party Destinations
    private String city;
    private String address;
    private int pricePerNight;
    private double rating;
    private String amenities;   // "WiFi, Pool, Gym, Spa"
    private String image;
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(int pricePerNight) { this.pricePerNight = pricePerNight; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}