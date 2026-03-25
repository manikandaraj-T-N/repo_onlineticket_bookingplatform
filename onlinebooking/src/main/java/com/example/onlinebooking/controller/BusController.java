package com.example.onlinebooking.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onlinebooking.model.Bus;
import com.example.onlinebooking.model.BusBooking;
import com.example.onlinebooking.repository.BusBookingRepository;
import com.example.onlinebooking.service.BusService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class BusController {

    @Autowired
    private BusService busService;

    @Autowired
    private BusBookingRepository busBookingRepository;


@GetMapping("/buses")
public String filterBuses(
        @RequestParam(required = false) Integer maxPrice,
        @RequestParam(required = false) List<String> busType,
        @RequestParam(required = false) List<String> departure,
        @RequestParam(required = false) String from,
        @RequestParam(required = false) String to,
        @RequestParam(required = false) String date,
        @RequestParam(required = false) String operator,
        @RequestParam(required = false) String boardingPoint,
        @RequestParam(defaultValue = "0") int page,
        Model model) {

    Page<Bus> busPage = busService.filterBuses(
            maxPrice, busType, departure, from, to, date, operator, page);

    model.addAttribute("buses", busPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", busPage.getTotalPages());
    model.addAttribute("totalBuses", busPage.getTotalElements());
    model.addAttribute("from", from);
    model.addAttribute("to", to);
    model.addAttribute("date", date);

    // if AJAX request return only fragment
    return "homebus";
}

@GetMapping("/homebus")
public String homeBus(
        @RequestParam(defaultValue = "0") int page,
        Model model) {

    Page<Bus> busPage = busService.filterBuses(
            null, null, null, null, null, null, null, page);

    model.addAttribute("buses", busPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", busPage.getTotalPages());
    model.addAttribute("totalBuses", busPage.getTotalElements());
    return "homebus";
}
   
    
 @PostMapping("/busbooking/confirm")
public String confirmBusBooking(
        @RequestParam Long busId,
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String phone,
        @RequestParam int seats,
        @RequestParam String boardingPoint,
        @RequestParam(required = false) String selectedSeats,
        Model model) {

    BusBooking booking = new BusBooking();
    booking.setBusId(busId);
    booking.setFirstName(firstName);
    booking.setLastName(lastName);
    booking.setPhone(phone);
    booking.setSeats(seats);
    booking.setBoardingPoint(boardingPoint);
    booking.setSelectedSeats(selectedSeats);
    booking.setBookingDate(LocalDate.now().toString());
    booking.setBookingStatus("Confirmed");

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    booking.setEmail(auth.getName());

    busBookingRepository.save(booking);

    Bus bus = busService.getBusById(busId);
    model.addAttribute("booking", booking);
    model.addAttribute("bus", bus);

    return "busconfirm";
}

@GetMapping("/busbooking/{id}")
public String busBookingPage(@PathVariable Long id, Model model) {
    Bus bus = busService.getBusById(id);

    List<BusBooking> existingBookings = busBookingRepository.findByBusId(id);
    
    List<String> bookedSeats = existingBookings.stream()
            .filter(b -> b.getSelectedSeats() != null)
            .filter(b -> "Confirmed".equals(b.getBookingStatus())) 
            .flatMap(b -> Arrays.stream(b.getSelectedSeats().split(",")))
            .map(s -> s.trim())
            .collect(Collectors.toList());

    model.addAttribute("bus", bus);
    model.addAttribute("bookedSeats", bookedSeats);
    return "busbooking";
}

@PostMapping("/busbooking/cancel/{id}")
public String cancelBus(@PathVariable Long id) {
    BusBooking booking = busBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    booking.setBookingStatus("Cancelled");
    busBookingRepository.save(booking);
    return "redirect:/mybookings";
}


@GetMapping("/busbooking/detail/{id}")
public String busBookingDetail(@PathVariable Long id, Model model) {
    BusBooking booking = busBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Bus bus = busService.getBusById(booking.getBusId());
    model.addAttribute("booking", booking);
    model.addAttribute("bus", bus);
    return "busbookingdetail";
}

@GetMapping("/busbooking/download/{id}")
public void downloadBusTicket(@PathVariable Long id, HttpServletResponse response) throws Exception {

    BusBooking booking = busBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Bus bus = busService.getBusById(booking.getBusId());

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=bus-ticket-" + id + ".pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();

    Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
    Font valueFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

    document.add(new Paragraph("TicketVerse - Bus Ticket", titleFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Booking Reference: BUS-" + booking.getId(), labelFont));
    document.add(new Paragraph("Booking Date: " + booking.getBookingDate(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("---- Bus Details ----", labelFont));
    document.add(new Paragraph("Bus: " + bus.getBusName(), valueFont));
    document.add(new Paragraph("Bus Number: " + bus.getBusNumber(), valueFont));
    document.add(new Paragraph("Type: " + bus.getBusType(), valueFont));
    document.add(new Paragraph("From: " + bus.getFromPlace(), valueFont));
    document.add(new Paragraph("To: " + bus.getToPlace(), valueFont));
    document.add(new Paragraph("Date: " + bus.getTravelDate(), valueFont));
    document.add(new Paragraph("Departure: " + bus.getDepartureTime(), valueFont));
    document.add(new Paragraph("Arrival: " + bus.getArrivalTime(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("---- Passenger Details ----", labelFont));
    document.add(new Paragraph("Name: " + booking.getFirstName() + " " + booking.getLastName(), valueFont));
    document.add(new Paragraph("Phone: " + booking.getPhone(), valueFont));
    document.add(new Paragraph("Seats: " + booking.getSeats(), valueFont));
    document.add(new Paragraph("Boarding Point: " + booking.getBoardingPoint(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Total Amount Paid: Rs." + bus.getPrice(), labelFont));

    document.close();
}
}