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

import com.example.onlinebooking.model.Activity;
import com.example.onlinebooking.model.ActivityBooking;
import com.example.onlinebooking.repository.ActivityBookingRepository;
import com.example.onlinebooking.service.ActivityService;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;


import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
   private ActivityBookingRepository activityBookingRepository;

    @GetMapping("/homeactivities")
    public String homeActivities(Model model) {
        model.addAttribute("activities", activityService.getAllActivities());
        return "homeactivities";
    }

    @GetMapping("/activities")
    public String filterActivities(
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String city,
            Model model) {

        List<Activity> activities = activityService.filterActivities(maxPrice, category, city);
        model.addAttribute("activities", activities);
        return "homeactivities";
    }

    @GetMapping("/activitybooking/{id}")
public String activityBookingPage(@PathVariable Long id, Model model) {
    model.addAttribute("activity", activityService.getActivityById(id));
    return "activitybooking";
}

@PostMapping("/activitybooking/confirm")
public String confirmActivityBooking(
        @RequestParam Long activityId,
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String phone,
        @RequestParam int participants,
        Model model) {

    ActivityBooking booking = new ActivityBooking();
    booking.setActivityId(activityId);
    booking.setFirstName(firstName);
    booking.setLastName(lastName);
    booking.setPhone(phone);
    booking.setParticipants(participants);
    booking.setBookingDate(LocalDate.now().toString());
    booking.setBookingStatus("Confirmed");

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    booking.setEmail(auth.getName());

    activityBookingRepository.save(booking);

    Activity activity = activityService.getActivityById(activityId);
    model.addAttribute("booking", booking);
    model.addAttribute("activity", activity);

    return "activityconfirm";
}


@PostMapping("/activitybooking/cancel/{id}")
public String cancelActivity(@PathVariable Long id) {
    ActivityBooking booking = activityBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    booking.setBookingStatus("Cancelled");
    activityBookingRepository.save(booking);
    return "redirect:/mybookings";
}


@GetMapping("/activitybooking/detail/{id}")
public String activityBookingDetail(@PathVariable Long id, Model model) {
    ActivityBooking booking = activityBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Activity activity = activityService.getActivityById(booking.getActivityId());
    model.addAttribute("booking", booking);
    model.addAttribute("activity", activity);
    return "activitybookingdetail";
}

@GetMapping("/activitybooking/download/{id}")
public void downloadActivityTicket(@PathVariable Long id, HttpServletResponse response) throws Exception {

    ActivityBooking booking = activityBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Activity activity = activityService.getActivityById(booking.getActivityId());

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=activity-ticket-" + id + ".pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();

    Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
    Font valueFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

    document.add(new Paragraph("TicketVerse - Activity Ticket", titleFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Booking Reference: ACT-" + booking.getId(), labelFont));
    document.add(new Paragraph("Booking Date: " + booking.getBookingDate(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("---- Activity Details ----", labelFont));
    document.add(new Paragraph("Activity: " + activity.getName(), valueFont));
    document.add(new Paragraph("Category: " + activity.getCategory(), valueFont));
    document.add(new Paragraph("City: " + activity.getCity(), valueFont));
    document.add(new Paragraph("Rating: " + activity.getRating(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("---- Participant Details ----", labelFont));
    document.add(new Paragraph("Name: " + booking.getFirstName() + " " + booking.getLastName(), valueFont));
    document.add(new Paragraph("Phone: " + booking.getPhone(), valueFont));
    document.add(new Paragraph("Participants: " + booking.getParticipants(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Total Amount Paid: Rs." + (activity.getPrice() * booking.getParticipants()), labelFont));

    document.close();
}
}