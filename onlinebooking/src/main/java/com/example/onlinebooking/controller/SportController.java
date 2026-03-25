package com.example.onlinebooking.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onlinebooking.model.Sport;
import com.example.onlinebooking.model.SportBooking;
import com.example.onlinebooking.repository.SportBookingRepository;
import com.example.onlinebooking.service.SportService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class SportController {

    @Autowired
    private SportService sportService;

    @Autowired
    private SportBookingRepository sportBookingRepository;

    @GetMapping("/homesports")
    public String homeSports(Model model) {
        model.addAttribute("sports", sportService.getAllSports());
        return "homesports";
    }

    @GetMapping("/sports")
    public String filterSports(
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String city,
            Model model) {
        model.addAttribute("sports", sportService.filterSports(maxPrice, category, city));
        return "homesports";
    }

    @GetMapping("/sportbooking/{id}")
    public String sportBookingPage(@PathVariable Long id, Model model) {
        model.addAttribute("sport", sportService.getSportById(id));
        return "sportbooking";
    }

    @PostMapping("/sportbooking/confirm")
     public String confirmSportBooking(
            @RequestParam Long sportId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam int tickets,
            Model model) {

        SportBooking booking = new SportBooking();
        booking.setSportId(sportId);
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setPhone(phone);
        booking.setTickets(tickets);
        booking.setBookingDate(LocalDate.now().toString());
        booking.setBookingStatus("Confirmed");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        booking.setEmail(auth.getName());

        sportBookingRepository.save(booking);

        Sport sport = sportService.getSportById(sportId);
        model.addAttribute("booking", booking);
        model.addAttribute("sport", sport);

        return "sportconfirm";
    }

@PostMapping("/sportbooking/cancel/{id}")
public String cancelSport(@PathVariable Long id) {
    SportBooking booking = sportBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    booking.setBookingStatus("Cancelled");
    sportBookingRepository.save(booking);
    return "redirect:/mybookings";
}


@GetMapping("/sportbooking/detail/{id}")
public String sportBookingDetail(@PathVariable Long id, Model model) {
    SportBooking booking = sportBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Sport sport = sportService.getSportById(booking.getSportId());
    model.addAttribute("booking", booking);
    model.addAttribute("sport", sport);
    return "sportbookingdetail";
}

    @GetMapping("/sportbooking/download/{id}")
public void downloadSportTicket(@PathVariable Long id, HttpServletResponse response) throws Exception {

    SportBooking booking = sportBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Sport sport = sportService.getSportById(booking.getSportId());

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=sport-ticket-" + id + ".pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();

    Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
    Font valueFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

    document.add(new Paragraph("TicketVerse - Sports Ticket", titleFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Booking Reference: SPT-" + booking.getId(), labelFont));
    document.add(new Paragraph("Booking Date: " + booking.getBookingDate(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("---- Match Details ----", labelFont));
    document.add(new Paragraph("Event: " + sport.getName(), valueFont));
    document.add(new Paragraph("Category: " + sport.getCategory(), valueFont));
    document.add(new Paragraph("Teams: " + sport.getMatchTeams(), valueFont));
    document.add(new Paragraph("Date: " + sport.getEventDate(), valueFont));
    document.add(new Paragraph("Time: " + sport.getEventTime(), valueFont));
    document.add(new Paragraph("Venue: " + sport.getVenue() + ", " + sport.getCity(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("---- Attendee Details ----", labelFont));
    document.add(new Paragraph("Name: " + booking.getFirstName() + " " + booking.getLastName(), valueFont));
    document.add(new Paragraph("Phone: " + booking.getPhone(), valueFont));
    document.add(new Paragraph("Tickets: " + booking.getTickets(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Total Amount Paid: Rs." + (sport.getPrice() * booking.getTickets()), labelFont));

    document.close();
}
}