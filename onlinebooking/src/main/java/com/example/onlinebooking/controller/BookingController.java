package com.example.onlinebooking.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onlinebooking.model.ActivityBooking;
import com.example.onlinebooking.model.BusBooking;
import com.example.onlinebooking.model.EventBooking;
import com.example.onlinebooking.model.Flight;
import com.example.onlinebooking.model.FlightBooking;
import com.example.onlinebooking.model.HotelBooking;
import com.example.onlinebooking.model.MovieBooking;
import com.example.onlinebooking.model.SportBooking;
import com.example.onlinebooking.model.TrainBooking;
import com.example.onlinebooking.model.User;
import com.example.onlinebooking.repository.ActivityBookingRepository;
import com.example.onlinebooking.repository.BusBookingRepository;
import com.example.onlinebooking.repository.EventBookingRepository;
import com.example.onlinebooking.repository.FlightBookingRepository;
import com.example.onlinebooking.repository.HotelBookingRepository;
import com.example.onlinebooking.repository.MovieBookingRepository;
import com.example.onlinebooking.repository.SportBookingRepository;
import com.example.onlinebooking.repository.TrainBookingRepository;
import com.example.onlinebooking.repository.UserRepository;
import com.example.onlinebooking.service.ActivityService;
import com.example.onlinebooking.service.BusService;
import com.example.onlinebooking.service.EventService;
import com.example.onlinebooking.service.FlightService;
import com.example.onlinebooking.service.HotelService;
import com.example.onlinebooking.service.MovieService;
import com.example.onlinebooking.service.SportService;
import com.example.onlinebooking.service.TrainService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class BookingController {

    @Autowired
    private FlightBookingRepository bookingRepository;

    @Autowired
    private ActivityBookingRepository activityBookingRepository; 

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlightService flightService;

    @Autowired
    private SportBookingRepository sportBookingRepository;

    @Autowired
    private SportService sportService;

    @Autowired
    private TrainBookingRepository trainBookingRepository;

    @Autowired
    private TrainService trainService;

    @Autowired
    private BusBookingRepository busBookingRepository;

    @Autowired
    private BusService busService;

    @Autowired
    private EventBookingRepository eventBookingRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private HotelBookingRepository hotelBookingRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private MovieBookingRepository movieBookingRepository;

    @Autowired
    private MovieService movieService;
    

    @PostMapping("/booking/confirm")
    public String confirmBooking(
        @RequestParam Long flightId,
        @RequestParam String travelClass,
        @RequestParam int passengers,
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String email,
        @RequestParam String phone,
        @RequestParam String dob,
        @RequestParam String gender,
        @RequestParam String idType,
        @RequestParam String idNumber,
        @RequestParam(required = false) String selectedSeats,
        Model model) {

    FlightBooking booking = new FlightBooking();
    booking.setFlightId(flightId);
    booking.setTravelClass(travelClass);
    booking.setPassengers(passengers);
    booking.setFirstName(firstName);
    booking.setLastName(lastName);
    booking.setEmail(email);
    booking.setPhone(phone);
    booking.setDob(dob);
    booking.setGender(gender);
    booking.setIdType(idType);
    booking.setIdNumber(idNumber);
    booking.setSelectedSeats(selectedSeats);
    booking.setBookingDate(LocalDate.now().toString());
    booking.setBookingStatus("Confirmed");

    // store logged-in user's email to link booking to user
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    System.out.println(auth.getName());
    String loggedInEmail = auth.getName();
    booking.setEmail(loggedInEmail);  // ✅ store in email field directly

    bookingRepository.save(booking);

    Flight flight = flightService.getFlightById(flightId);

    model.addAttribute("booking", booking);
    model.addAttribute("flight", flight);

    return "bookingconfirm";
}


@GetMapping("/mybookings")
    public String myBookings(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String loggedInEmail = auth.getName();

    User user = userRepository.findByEmail(loggedInEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // ✅ fetch ALL booking types
    List<FlightBooking> flightBookings = bookingRepository.findByEmail(loggedInEmail);
    List<ActivityBooking> activityBookings = activityBookingRepository.findByEmail(loggedInEmail);
    List<SportBooking> sportBookings = sportBookingRepository.findByEmail(loggedInEmail);
    List<TrainBooking> trainBookings = trainBookingRepository.findByEmail(loggedInEmail);
    List<BusBooking> busBookings = busBookingRepository.findByEmail(loggedInEmail);
    List<EventBooking> eventBookings = eventBookingRepository.findByEmail(loggedInEmail);
    List<MovieBooking> movieBookings = movieBookingRepository.findByEmail(loggedInEmail);
    List<HotelBooking> hotelBookings = hotelBookingRepository.findByEmail(loggedInEmail);

    // ✅ build detail maps
    List<Map<String, Object>> flightDetails = new ArrayList<>();
    for (FlightBooking b : flightBookings) {
        Map<String, Object> item = new HashMap<>();
        item.put("booking", b);
        item.put("flight", flightService.getFlightById(b.getFlightId()));
        flightDetails.add(item);
    }

    List<Map<String, Object>> activityDetails = new ArrayList<>();
    for (ActivityBooking b : activityBookings) {
        Map<String, Object> item = new HashMap<>();
        item.put("booking", b);
        item.put("activity", activityService.getActivityById(b.getActivityId()));
        activityDetails.add(item);
    }

    List<Map<String, Object>> sportDetails = new ArrayList<>();
    for (SportBooking b : sportBookings) {
        Map<String, Object> item = new HashMap<>();
        item.put("booking", b);
        item.put("sport", sportService.getSportById(b.getSportId()));
        sportDetails.add(item);
    }

    List<Map<String, Object>> trainDetails = new ArrayList<>();
    for (TrainBooking b : trainBookings) {
        Map<String, Object> item = new HashMap<>();
        item.put("booking", b);
        item.put("train", trainService.getTrainById(b.getTrainId()));
        trainDetails.add(item);
    }

    List<Map<String, Object>> busDetails = new ArrayList<>();
    for (BusBooking b : busBookings) {
        Map<String, Object> item = new HashMap<>();
        item.put("booking", b);
        item.put("bus", busService.getBusById(b.getBusId()));
        busDetails.add(item);
    }

    List<Map<String, Object>> eventDetails = new ArrayList<>();
    for (EventBooking b : eventBookings) {
        Map<String, Object> item = new HashMap<>();
        item.put("booking", b);
        item.put("event", eventService.getEventById(b.getEventId()));
        eventDetails.add(item);
    }

    List<Map<String, Object>> movieDetails = new ArrayList<>();
    for (MovieBooking b : movieBookings) {
        Map<String, Object> item = new HashMap<>();
        item.put("booking", b);
        item.put("movie", movieService.getMovieById(b.getMovieId()));
        movieDetails.add(item);
    }

    List<Map<String, Object>> hotelDetails = new ArrayList<>();
    for (HotelBooking b : hotelBookings) {
        Map<String, Object> item = new HashMap<>();
        item.put("booking", b);
        item.put("hotel", hotelService.getHotelById(b.getHotelId()));
        hotelDetails.add(item);
    }

    // ✅ total count from ALL 8 tables
    int totalBookings = flightBookings.size() + activityBookings.size()
            + sportBookings.size() + trainBookings.size() + busBookings.size()
            + eventBookings.size() + movieBookings.size() + hotelBookings.size();

    // ✅ confirmed count from ALL 8 tables
    long confirmed = flightBookings.stream().filter(b -> "Confirmed".equals(b.getBookingStatus())).count()
            + activityBookings.stream().filter(b -> "Confirmed".equals(b.getBookingStatus())).count()
            + sportBookings.stream().filter(b -> "Confirmed".equals(b.getBookingStatus())).count()
            + trainBookings.stream().filter(b -> "Confirmed".equals(b.getBookingStatus())).count()
            + busBookings.stream().filter(b -> "Confirmed".equals(b.getBookingStatus())).count()
            + eventBookings.stream().filter(b -> "Confirmed".equals(b.getBookingStatus())).count()
            + movieBookings.stream().filter(b -> "Confirmed".equals(b.getBookingStatus())).count()
            + hotelBookings.stream().filter(b -> "Confirmed".equals(b.getBookingStatus())).count();

    // ✅ pending count from ALL 8 tables
    long pending = flightBookings.stream().filter(b -> "Pending".equals(b.getBookingStatus())).count()
            + activityBookings.stream().filter(b -> "Pending".equals(b.getBookingStatus())).count()
            + sportBookings.stream().filter(b -> "Pending".equals(b.getBookingStatus())).count()
            + trainBookings.stream().filter(b -> "Pending".equals(b.getBookingStatus())).count()
            + busBookings.stream().filter(b -> "Pending".equals(b.getBookingStatus())).count()
            + eventBookings.stream().filter(b -> "Pending".equals(b.getBookingStatus())).count()
            + movieBookings.stream().filter(b -> "Pending".equals(b.getBookingStatus())).count()
            + hotelBookings.stream().filter(b -> "Pending".equals(b.getBookingStatus())).count();

    // ✅ add all to model — only ONCE each
    model.addAttribute("username", user.getName());
    model.addAttribute("flightDetails", flightDetails);
    model.addAttribute("activityDetails", activityDetails);
    model.addAttribute("sportDetails", sportDetails);
    model.addAttribute("trainDetails", trainDetails);
    model.addAttribute("busDetails", busDetails);
    model.addAttribute("eventDetails", eventDetails);
    model.addAttribute("movieDetails", movieDetails);
    model.addAttribute("hotelDetails", hotelDetails);
    model.addAttribute("totalBookings", totalBookings);
    model.addAttribute("confirmed", confirmed);
    model.addAttribute("pending", pending);

    return "mybookings";
}


@GetMapping("/booking/{id}")
public String flightBookingPage(@PathVariable Long id, Model model) {
    Flight flight = flightService.getFlightById(id);

    List<FlightBooking> existingBookings = bookingRepository.findByFlightId(id);
    List<String> bookedSeats = existingBookings.stream()
            .filter(b -> b.getSelectedSeats() != null)
            .filter(b -> "Confirmed".equals(b.getBookingStatus())) 
            .flatMap(b -> Arrays.stream(b.getSelectedSeats().split(",")))
            .map(s -> s.trim())
            .collect(Collectors.toList());

    model.addAttribute("flight", flight);
    model.addAttribute("bookedSeats", bookedSeats);
    return "flightbooking";
}

@PostMapping("/booking/cancel/{id}")
public String cancelFlight(@PathVariable Long id) {
    FlightBooking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    booking.setBookingStatus("Cancelled");
    bookingRepository.save(booking);
    return "redirect:/mybookings";
}

@GetMapping("/booking/detail/{id}")
public String flightBookingDetail(@PathVariable Long id, Model model) {
    FlightBooking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Flight flight = flightService.getFlightById(booking.getFlightId());
    model.addAttribute("booking", booking);
    model.addAttribute("flight", flight);
    return "bookingdetail";
}

@GetMapping("/booking/download/{id}")
public void downloadTicket(@PathVariable Long id, HttpServletResponse response) throws Exception {

    FlightBooking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    Flight flight = flightService.getFlightById(booking.getFlightId());

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=ticket-BK-" + id + ".pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();

    // title
    Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
    Font valueFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

    document.add(new Paragraph("TicketVerse - Flight Ticket", titleFont));
    document.add(new Paragraph(" "));

    document.add(new Paragraph("Booking Reference: BK-" + booking.getId(), labelFont));
    document.add(new Paragraph("Booking Date: " + booking.getBookingDate(), valueFont));
    document.add(new Paragraph(" "));

    document.add(new Paragraph("---- Flight Details ----", labelFont));
    document.add(new Paragraph("Airline: " + flight.getAirline(), valueFont));
    document.add(new Paragraph("Flight Number: " + flight.getFlightNumber(), valueFont));
    document.add(new Paragraph("From: " + flight.getFromPlace(), valueFont));
    document.add(new Paragraph("To: " + flight.getToPlace(), valueFont));
    document.add(new Paragraph("Date: " + flight.getTravelDate(), valueFont));
    document.add(new Paragraph("Departure: " + flight.getDepartureTime(), valueFont));
    document.add(new Paragraph("Arrival: " + flight.getArrivalTime(), valueFont));
    document.add(new Paragraph("Stop: " + flight.getStop(), valueFont));
    document.add(new Paragraph(" "));

    document.add(new Paragraph("---- Passenger Details ----", labelFont));
    document.add(new Paragraph("Name: " + booking.getFirstName() + " " + booking.getLastName(), valueFont));
    document.add(new Paragraph("Email: " + booking.getEmail(), valueFont));
    document.add(new Paragraph("Phone: " + booking.getPhone(), valueFont));
    document.add(new Paragraph("Class: " + booking.getTravelClass(), valueFont));
    document.add(new Paragraph("Passengers: " + booking.getPassengers(), valueFont));
    document.add(new Paragraph("Selected Seats: " + booking.getSelectedSeats(), valueFont));
    document.add(new Paragraph(" "));

   document.add(new Paragraph("Total Amount Paid: Rs." + (flight.getPrice() * booking.getPassengers()), labelFont));

    document.close();
}
}