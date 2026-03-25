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

import com.example.onlinebooking.model.Train;
import com.example.onlinebooking.model.TrainBooking;
import com.example.onlinebooking.repository.TrainBookingRepository;
import com.example.onlinebooking.service.TrainService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class TrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private TrainBookingRepository trainBookingRepository;

    @GetMapping("/hometrain")
    public String homeTrain(Model model) {
        model.addAttribute("trains", trainService.getAllTrains());
        return "hometrain";
    }

    @GetMapping("/trains")
    public String filterTrains(
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) List<String> trainType,
            @RequestParam(required = false) List<String> departure,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String date,
          
            Model model) {

        model.addAttribute("trains", trainService.filterTrains(maxPrice, trainType, departure, from, to, date));
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("date", date);
        return "hometrain";
    }

    @GetMapping("/trainbooking/{id}")
    public String trainBookingPage(@PathVariable Long id, Model model) {
        model.addAttribute("train", trainService.getTrainById(id));
        return "trainbooking";
    }

    @PostMapping("/trainbooking/confirm")
    public String confirmTrainBooking(
            @RequestParam Long trainId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam String seatClass,
            @RequestParam int passengers,
            @RequestParam(required = false) String berth,
            Model model) {

        TrainBooking booking = new TrainBooking();
        booking.setTrainId(trainId);
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setPhone(phone);
        booking.setSeatClass(seatClass);
        booking.setPassengers(passengers);
        booking.setBookingDate(LocalDate.now().toString());
        booking.setBerth(berth);
        booking.setBookingStatus("Confirmed");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        booking.setEmail(auth.getName());

        trainBookingRepository.save(booking);

        Train train = trainService.getTrainById(trainId);
        model.addAttribute("booking", booking);
        model.addAttribute("train", train);

        return "trainconfirm";
    }


    @GetMapping("/trainbooking/detail/{id}")
public String trainBookingDetail(@PathVariable Long id, Model model) {
    TrainBooking booking = trainBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Train train = trainService.getTrainById(booking.getTrainId());
    model.addAttribute("booking", booking);
    model.addAttribute("train", train);
    return "trainbookingdetail";
}


    @GetMapping("/trainbooking/download/{id}")
    public void downloadTrainTicket(@PathVariable Long id, HttpServletResponse response) throws Exception {

    TrainBooking booking = trainBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Train train = trainService.getTrainById(booking.getTrainId());

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=train-ticket-" + id + ".pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();

    Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
    Font valueFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

    document.add(new Paragraph("TicketVerse - Train Ticket", titleFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Booking Reference: TRN-" + booking.getId(), labelFont));
    document.add(new Paragraph("Booking Date: " + booking.getBookingDate(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("---- Train Details ----", labelFont));
    document.add(new Paragraph("Train: " + train.getTrainName(), valueFont));
    document.add(new Paragraph("Train No: " + train.getTrainNumber(), valueFont));
    document.add(new Paragraph("Type: " + train.getTrainType(), valueFont));
    document.add(new Paragraph("From: " + train.getFromPlace(), valueFont));
    document.add(new Paragraph("To: " + train.getToPlace(), valueFont));
    document.add(new Paragraph("Date: " + train.getTravelDate(), valueFont));
    document.add(new Paragraph("Departure: " + train.getDepartureTime(), valueFont));
    document.add(new Paragraph("Arrival: " + train.getArrivalTime(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("---- Passenger Details ----", labelFont));
    document.add(new Paragraph("Name: " + booking.getFirstName() + " " + booking.getLastName(), valueFont));
    document.add(new Paragraph("Phone: " + booking.getPhone(), valueFont));
    document.add(new Paragraph("Class: " + booking.getSeatClass(), valueFont));
    document.add(new Paragraph("Berth: " + booking.getBerth(), valueFont));
    document.add(new Paragraph("Passengers: " + booking.getPassengers(), valueFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Total Amount Paid: Rs." + (train.getPrice() * booking.getPassengers()), labelFont));

    document.close();
}
}