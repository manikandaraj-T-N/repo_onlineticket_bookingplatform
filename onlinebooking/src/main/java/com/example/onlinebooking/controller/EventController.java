package com.example.onlinebooking.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onlinebooking.model.Event;
import com.example.onlinebooking.model.EventBooking;
import com.example.onlinebooking.repository.EventBookingRepository;
import com.example.onlinebooking.service.EventService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventBookingRepository eventBookingRepository;

    @GetMapping("/homeevents")
    public String homeEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "homeevents";
    }

    @GetMapping("/events")
    public String filterEvents(
            @RequestParam(required = false) List<String> category,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Integer maxPrice,
            Model model) {
        model.addAttribute("events", eventService.filterEvents(category, city, maxPrice));
        return "homeevents";
    }

    @GetMapping("/eventbooking/{id}")
    public String eventBookingPage(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        return "eventbooking";
    }

    @PostMapping("/eventbooking/confirm")
    public String confirmEventBooking(
            @RequestParam Long eventId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam int tickets,
            Model model) {

        EventBooking booking = new EventBooking();
        booking.setEventId(eventId);
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setPhone(phone);
        booking.setTickets(tickets);
        booking.setBookingDate(LocalDate.now().toString());
        booking.setBookingStatus("Confirmed");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        booking.setEmail(auth.getName());

        eventBookingRepository.save(booking);

        Event event = eventService.getEventById(eventId);
        model.addAttribute("booking", booking);
        model.addAttribute("event", event);

        return "eventconfirm";
    }
 
    @PostMapping("/eventbooking/cancel/{id}")
public String cancelEvent(@PathVariable Long id) {
    EventBooking booking = eventBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    booking.setBookingStatus("Cancelled");
    eventBookingRepository.save(booking);
    return "redirect:/mybookings";
}

@GetMapping("/eventbooking/detail/{id}")
public String eventBookingDetail(@PathVariable Long id, Model model) {
    EventBooking booking = eventBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Event event = eventService.getEventById(booking.getEventId());
    model.addAttribute("booking", booking);
    model.addAttribute("event", event);
    return "eventbookingdetail";
}

    @GetMapping("/eventbooking/download/{id}")
public void downloadEventTicket(@PathVariable Long id, HttpServletResponse response) throws Exception {

    EventBooking booking = eventBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Event event = eventService.getEventById(booking.getEventId());

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=event-ticket-" + id + ".pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();

    Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
    Font valueFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

    document.add(new Paragraph("TicketVerse - Event Ticket", titleFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Booking Reference: EVT-" + booking.getId(), labelFont));
    document.add(new Paragraph("Booking Date: " + booking.getBookingDate(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("---- Event Details ----", labelFont));
    document.add(new Paragraph("Event: " + event.getName(), valueFont));
    document.add(new Paragraph("Category: " + event.getCategory(), valueFont));
    document.add(new Paragraph("Date: " + event.getEventDate(), valueFont));
    document.add(new Paragraph("Time: " + event.getEventTime(), valueFont));
    document.add(new Paragraph("Venue: " + event.getVenue(), valueFont));
    document.add(new Paragraph("City: " + event.getCity(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("---- Attendee Details ----", labelFont));
    document.add(new Paragraph("Name: " + booking.getFirstName() + " " + booking.getLastName(), valueFont));
    document.add(new Paragraph("Phone: " + booking.getPhone(), valueFont));
    document.add(new Paragraph("Tickets: " + booking.getTickets(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Total Amount Paid: Rs." + (event.getPrice() * booking.getTickets()), labelFont));

    document.close();
}
}