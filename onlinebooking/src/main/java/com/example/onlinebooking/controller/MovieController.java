package com.example.onlinebooking.controller;

import com.example.onlinebooking.model.Movie;
import com.example.onlinebooking.model.MovieBooking;
import com.example.onlinebooking.repository.MovieBookingRepository;
import com.example.onlinebooking.service.MovieService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieBookingRepository movieBookingRepository;

    @GetMapping("/homemovies")
    public String homeMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "homemovies";
    }

    @GetMapping("/movies")
    public String filterMovies(
            @RequestParam(required = false) List<String> genre,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String title,
            Model model) {
        model.addAttribute("movies", movieService.filterMovies(genre, language, status, title));
        return "homemovies";
    }

    @GetMapping("/moviebooking/{id}")
    public String movieBookingPage(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "moviebooking";
    }

    @PostMapping("/moviebooking/confirm")
    public String confirmMovieBooking(
            @RequestParam Long movieId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam int tickets,
            @RequestParam String seatType,
            Model model) {

        MovieBooking booking = new MovieBooking();
        booking.setMovieId(movieId);
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setPhone(phone);
        booking.setTickets(tickets);
        booking.setSeatType(seatType);
        booking.setBookingDate(LocalDate.now().toString());
        booking.setBookingStatus("Confirmed");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        booking.setEmail(auth.getName());

        movieBookingRepository.save(booking);

        Movie movie = movieService.getMovieById(movieId);
        model.addAttribute("booking", booking);
        model.addAttribute("movie", movie);

        return "movieconfirm";
    }


    @GetMapping("/moviebooking/detail/{id}")
public String movieBookingDetail(@PathVariable Long id, Model model) {
    MovieBooking booking = movieBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Movie movie = movieService.getMovieById(booking.getMovieId());
    model.addAttribute("booking", booking);
    model.addAttribute("movie", movie);
    return "moviebookingdetail";
}

    @GetMapping("/moviebooking/download/{id}")
    public void downloadMovieTicket(@PathVariable Long id, HttpServletResponse response) throws Exception {

        MovieBooking booking = movieBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        Movie movie = movieService.getMovieById(booking.getMovieId());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=movie-ticket-" + id + ".pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
        Font valueFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

        document.add(new Paragraph("TicketVerse - Movie Ticket", titleFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Booking Reference: MOV-" + booking.getId(), labelFont));
        document.add(new Paragraph("Booking Date: " + booking.getBookingDate(), valueFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("---- Movie Details ----", labelFont));
        document.add(new Paragraph("Movie: " + movie.getTitle(), valueFont));
        document.add(new Paragraph("Language: " + movie.getLanguage(), valueFont));
        document.add(new Paragraph("Genre: " + movie.getGenre(), valueFont));
        document.add(new Paragraph("Duration: " + movie.getDuration(), valueFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("---- Booking Details ----", labelFont));
        document.add(new Paragraph("Name: " + booking.getFirstName() + " " + booking.getLastName(), valueFont));
        document.add(new Paragraph("Phone: " + booking.getPhone(), valueFont));
        document.add(new Paragraph("Tickets: " + booking.getTickets(), valueFont));
        document.add(new Paragraph("Seat Type: " + booking.getSeatType(), valueFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Total Amount Paid: Rs." + (movie.getPrice() * booking.getTickets()), labelFont));

        document.close();
    }
}