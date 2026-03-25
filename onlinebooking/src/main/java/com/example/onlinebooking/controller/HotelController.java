package com.example.onlinebooking.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onlinebooking.model.Hotel;
import com.example.onlinebooking.model.HotelBooking;
import com.example.onlinebooking.repository.HotelBookingRepository;
import com.example.onlinebooking.service.HotelService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelBookingRepository hotelBookingRepository;

    @GetMapping("/homehotels")
    public String homeHotels(Model model) {
        model.addAttribute("hotels", hotelService.getAllHotels());
        return "homehotels";
    }

    @GetMapping("/hotels")
    public String filterHotels(
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer maxPrice,
            Model model) {
        model.addAttribute("hotels", hotelService.filterHotels(area, category, maxPrice));
        model.addAttribute("area", area);
        return "homehotels";
    }

    @GetMapping("/hotelbooking/{id}")
    public String hotelBookingPage(@PathVariable Long id, Model model) {
        model.addAttribute("hotel", hotelService.getHotelById(id));
        return "hotelbooking";
    }

    @PostMapping("/hotelbooking/confirm")
    public String confirmHotelBooking(
            @RequestParam Long hotelId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            @RequestParam int guests,
            Model model) {

        Hotel hotel = hotelService.getHotelById(hotelId);

        // calculate nights
        LocalDate in = LocalDate.parse(checkIn);
        LocalDate out = LocalDate.parse(checkOut);
        int nights = (int) ChronoUnit.DAYS.between(in, out);
        if (nights < 1) nights = 1;

        int totalPrice = hotel.getPricePerNight() * nights;

        HotelBooking booking = new HotelBooking();
        booking.setHotelId(hotelId);
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setPhone(phone);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setGuests(guests);
        booking.setNights(nights);
        booking.setTotalPrice(totalPrice);
        booking.setBookingDate(LocalDate.now().toString());
        booking.setBookingStatus("Confirmed");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        booking.setEmail(auth.getName());

        hotelBookingRepository.save(booking);

        model.addAttribute("booking", booking);
        model.addAttribute("hotel", hotel);

        return "hotelconfirm";
    }

@PostMapping("/hotelbooking/cancel/{id}")
public String cancelHotel(@PathVariable Long id) {
    HotelBooking booking = hotelBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    booking.setBookingStatus("Cancelled");
    hotelBookingRepository.save(booking);
    return "redirect:/mybookings";
}

@GetMapping("/hotelbooking/detail/{id}")
public String hotelBookingDetail(@PathVariable Long id, Model model) {
    HotelBooking booking = hotelBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Hotel hotel = hotelService.getHotelById(booking.getHotelId());
    model.addAttribute("booking", booking);
    model.addAttribute("hotel", hotel);
    return "hotelbookingdetail";
}

    @GetMapping("/hotelbooking/download/{id}")
    public void downloadHotelTicket(@PathVariable Long id, HttpServletResponse response) throws Exception {

        HotelBooking booking = hotelBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        Hotel hotel = hotelService.getHotelById(booking.getHotelId());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=hotel-booking-" + id + ".pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
        Font valueFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

        document.add(new Paragraph("TicketVerse - Hotel Booking", titleFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Booking Reference: HTL-" + booking.getId(), labelFont));
        document.add(new Paragraph("Booking Date: " + booking.getBookingDate(), valueFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("---- Hotel Details ----", labelFont));
        document.add(new Paragraph("Hotel: " + hotel.getName(), valueFont));
        document.add(new Paragraph("Category: " + hotel.getCategory(), valueFont));
        document.add(new Paragraph("Address: " + hotel.getAddress(), valueFont));
        document.add(new Paragraph("City: " + hotel.getCity(), valueFont));
        document.add(new Paragraph("Amenities: " + hotel.getAmenities(), valueFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("---- Guest Details ----", labelFont));
        document.add(new Paragraph("Name: " + booking.getFirstName() + " " + booking.getLastName(), valueFont));
        document.add(new Paragraph("Phone: " + booking.getPhone(), valueFont));
        document.add(new Paragraph("Check In: " + booking.getCheckIn(), valueFont));
        document.add(new Paragraph("Check Out: " + booking.getCheckOut(), valueFont));
        document.add(new Paragraph("Nights: " + booking.getNights(), valueFont));
        document.add(new Paragraph("Guests: " + booking.getGuests(), valueFont));
        document.add(new Paragraph(" "));
        // document.add(new Paragraph("Price Per Night: Rs." + hotel.getPricePerNight(), valueFont));

        // document.add(new Paragraph("Total Amount Paid: Rs." + booking.getTotalPrice(), labelFont));

        document.add(new Paragraph("Total Amount Paid: Rs." + booking.getTotalPrice(), labelFont));
        document.close();
    }
}