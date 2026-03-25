create database ticketbooking;
use ticketbooking;
show tables;

INSERT INTO users (email, password)
VALUES ('test@gmail.com', '123456');

-- INSERT INTO flight (airline, arrival_time,departure_time,price,stop, from_place, to_place )
-- VALUES 
-- ('IndiGo', '08:00 AM', '12:45 PM',  5499, 'Non Stop','Chennai', 'Delhi'),
-- ('Air India', '09:45 AM', '11:15 AM',5850, 'Non Stop', 'Chennai', 'Mumbai' ),
-- ('Vistara', '02:00 PM', '05:00 PM',  7200, '1 Stop','Chennai', 'Bangalore'),
-- ('SpiceJet', '06:30 AM', '09:00 AM', 4999, 'Non Stop','Chennai', 'Hyderabad'),
-- ('Akasa Air', '03:15 PM', '06:00 PM',8100, 'Non Stop', 'Chennai', 'Delhi');

INSERT INTO flight (airline, departure_time, arrival_time, from_place, to_place, price, stop, travel_date)
VALUES 
('IndiGo', '08:00 AM', '12:45 PM', 'Chennai', 'Delhi', 5499, 'Non Stop', '2026-03-20'),
('Air India', '09:45 AM', '11:15 AM', 'Chennai', 'Mumbai', 5850, 'Non Stop', '2026-03-20'),
('Vistara', '02:00 PM', '05:00 PM', 'Chennai', 'Bangalore', 7200, '1 Stop', '2026-03-21');




ALTER TABLE flight ADD COLUMN travel_date VARCHAR(20);

INSERT INTO flight (airline, departure_time, arrival_time, from_place, to_place, price, stop, travel_date)
VALUES ('IndiGo', '08:00 AM', '12:45 PM', 'Chennai', 'Delhi', 5489, '1', '2026-03-21');

INSERT INTO flight (airline, departure_time, arrival_time, from_place, to_place, price, stop, travel_date)
VALUES ('Versta', '08:05 AM', '12:55 PM', 'Chennai', 'Delhi', 5589, 'NonStop', '2026-03-21');

ALTER TABLE flight ADD COLUMN image VARCHAR(255);

ALTER TABLE flight ADD COLUMN flight_number VARCHAR(20);
ALTER TABLE flight ADD COLUMN duration VARCHAR(20);

INSERT INTO flight (airline, stop, price, departure_time, arrival_time, from_place, to_place, travel_date, image, flight_number, duration)
VALUES 
('IndiGo',    'Non Stop', 5499, '08:00 AM', '12:45 PM', 'Chennai', 'Delhi',     '2026-03-20', 'indigoicon.png', '6E 204', '2h 45m'),
('Air India', 'Non Stop', 5850, '09:45 AM', '11:15 AM', 'Chennai', 'Mumbai',    '2026-03-20', 'airindia.png',  'AI 101', '2h 20m'),
('Vistara',   '1 Stop',   7200, '02:00 PM', '05:00 PM', 'Chennai', 'Bangalore', '2026-03-20', 'vistara.png',   'UK 802', '3h 00m'),
('SpiceJet',  'Non Stop', 4999, '06:30 AM', '09:00 AM', 'Chennai', 'Hyderabad', '2026-03-20', 'spicejet.png',  'SG 301', '2h 30m'),
('Akasa Air', 'Non Stop', 8100, '03:15 PM', '06:00 PM', 'Chennai', 'Delhi',     '2026-03-20', 'akasa.png',     'QP 112', '2h 45m');


INSERT INTO activity (name, category, city, price, rating, image) VALUES
('Skyline Zipline Adventure', 'Adventure', 'Munnar', 1200, 4.3, 'act-1.jpg'),
('Wonderla Amusement Park', 'Theme Parks', 'Bangalore', 1450, 4.3, 'act-2.png'),
('River Kayaking Experience', 'Water Sports', 'Coorg', 950, 4.3, 'act-3.jpg'),
('T-Rex Amusement Center', 'Indoor Games', 'Chennai', 600, 4.1, 'act-4.png'),
('Go Karting Experience', 'Adventure', 'Hyderabad', 800, 4.5, 'act-5.png'),
('Rock Climbing Wall', 'Adventure', 'Bangalore', 1100, 4.4, 'act-6.jpg');


INSERT INTO sport (name, category, venue, city, event_date, price, rating, image) VALUES
('IPL - CSK vs MI', 'Cricket', 'Chepauk Stadium', 'Chennai', '2026-04-15', 1500, 4.8, 'sport-1.jpg'),
('ISL - Chennaiyin FC vs Mumbai City', 'Football', 'Jawaharlal Nehru Stadium', 'Chennai', '2026-04-20', 800, 4.5, 'sport-2.jpg'),
('Pro Kabaddi - Tamil Thalaivas', 'Kabaddi', 'Nehru Indoor Stadium', 'Chennai', '2026-04-25', 500, 4.3, 'sport-3.jpg'),
('IPL - RCB vs KKR', 'Cricket', 'Chinnaswamy Stadium', 'Bangalore', '2026-04-18', 1800, 4.9, 'sport-4.jpg'),
('Badminton Premier League', 'Badminton', 'Gachibowli Indoor Stadium', 'Hyderabad', '2026-05-01', 600, 4.4, 'sport-5.jpg'),
('Tennis Open Championship', 'Tennis', 'DLTA Complex', 'Delhi', '2026-05-10', 1200, 4.6, 'sport-6.jpg');



DELETE FROM bus;
ALTER TABLE bus AUTO_INCREMENT = 1;

INSERT INTO bus (bus_name, bus_number, from_place, to_place, departure_time, arrival_time, travel_date, duration, bus_type, price) VALUES

-- Chennai to Bangalore
('TNSTC Volvo', 'TN01-1234', 'Chennai', 'Bangalore', '06:00 AM', '12:00 PM', '2026-04-15', '6h 00m', 'AC', 450),
('KPN Travels', 'TN09-9012', 'Chennai', 'Bangalore', '09:00 PM', '03:30 AM', '2026-04-15', '6h 30m', 'AC Sleeper', 650),
('SRM Travels', 'TN07-5678', 'Chennai', 'Bangalore', '10:00 PM', '04:30 AM', '2026-04-15', '6h 30m', 'Sleeper', 550),
('Orange Travels', 'TN45-3456', 'Chennai', 'Bangalore', '08:30 PM', '03:00 AM', '2026-04-15', '6h 30m', 'Semi-Sleeper', 500),
('VRL Travels', 'KA01-1234', 'Chennai', 'Bangalore', '07:00 PM', '01:30 AM', '2026-04-15', '6h 30m', 'AC Sleeper', 700),

-- Chennai to Coimbatore
('TNSTC Volvo', 'TN01-2345', 'Chennai', 'Coimbatore', '09:00 PM', '05:00 AM', '2026-04-15', '8h 00m', 'AC', 480),
('KPN Travels', 'TN09-6789', 'Chennai', 'Coimbatore', '10:30 PM', '06:30 AM', '2026-04-15', '8h 00m', 'AC Sleeper', 600),
('Parveen Travels', 'TN22-7890', 'Chennai', 'Coimbatore', '08:00 PM', '04:00 AM', '2026-04-15', '8h 00m', 'Non-AC', 280),
('SRS Travels', 'TN33-2345', 'Chennai', 'Coimbatore', '07:30 PM', '03:30 AM', '2026-04-15', '8h 00m', 'Non-AC', 260),

-- Chennai to Madurai
('KPN Travels', 'TN09-1122', 'Chennai', 'Madurai', '09:30 PM', '04:00 AM', '2026-04-15', '7h 30m', 'AC Sleeper', 550),
('SRS Travels', 'TN33-4455', 'Chennai', 'Madurai', '10:00 PM', '04:30 AM', '2026-04-15', '7h 00m', 'Non-AC', 300),
('Orange Travels', 'TN45-6677', 'Chennai', 'Madurai', '08:00 PM', '03:30 AM', '2026-04-15', '7h 30m', 'Semi-Sleeper', 450),
('TNSTC Volvo', 'TN01-3456', 'Chennai', 'Madurai', '07:00 PM', '02:30 AM', '2026-04-15', '7h 30m', 'AC', 500),

-- Chennai to Hyderabad
('VRL Travels', 'KA01-2345', 'Chennai', 'Hyderabad', '04:00 PM', '06:00 AM', '2026-04-15', '14h 00m', 'AC Sleeper', 900),
('Orange Travels', 'TN45-8899', 'Chennai', 'Hyderabad', '05:00 PM', '07:00 AM', '2026-04-15', '14h 00m', 'Semi-Sleeper', 700),
('SRM Travels', 'TN07-1122', 'Chennai', 'Hyderabad', '06:00 PM', '08:00 AM', '2026-04-15', '14h 00m', 'Sleeper', 750),

-- Chennai to Trichy
('TNSTC Volvo', 'TN01-3344', 'Chennai', 'Trichy', '06:00 AM', '11:30 AM', '2026-04-15', '5h 30m', 'AC', 350),
('Parveen Travels', 'TN22-5566', 'Chennai', 'Trichy', '07:00 AM', '12:30 PM', '2026-04-15', '5h 30m', 'Non-AC', 200),
('KPN Travels', 'TN09-3456', 'Chennai', 'Trichy', '08:00 AM', '01:30 PM', '2026-04-15', '5h 30m', 'AC', 380),

-- Chennai to Tirupati
('SRS Travels', 'TN33-7788', 'Chennai', 'Tirupati', '06:00 AM', '10:30 AM', '2026-04-15', '4h 30m', 'Non-AC', 250),
('KPN Travels', 'TN09-9900', 'Chennai', 'Tirupati', '07:00 AM', '11:30 AM', '2026-04-15', '4h 30m', 'AC', 380),
('TNSTC Volvo', 'TN01-4567', 'Chennai', 'Tirupati', '05:00 AM', '09:30 AM', '2026-04-15', '4h 30m', 'AC', 400),

-- Chennai to Pondicherry
('TNSTC Volvo', 'TN01-4444', 'Chennai', 'Pondicherry', '06:00 AM', '09:30 AM', '2026-04-15', '3h 30m', 'AC', 220),
('KPN Travels', 'TN09-5555', 'Chennai', 'Pondicherry', '08:00 AM', '11:30 AM', '2026-04-15', '3h 30m', 'Non-AC', 150),
('Parveen Travels', 'TN22-6666', 'Chennai', 'Pondicherry', '10:00 AM', '01:30 PM', '2026-04-15', '3h 30m', 'Non-AC', 160),
('SRS Travels', 'TN33-7777', 'Chennai', 'Pondicherry', '02:00 PM', '05:30 PM', '2026-04-15', '3h 30m', 'AC', 230),

-- Chennai to Salem
('TNSTC Volvo', 'TN01-8888', 'Chennai', 'Salem', '07:00 AM', '12:30 PM', '2026-04-15', '5h 30m', 'AC', 320),
('KPN Travels', 'TN09-9999', 'Chennai', 'Salem', '09:00 PM', '02:30 AM', '2026-04-15', '5h 30m', 'AC Sleeper', 480),
('Orange Travels', 'TN45-1010', 'Chennai', 'Salem', '08:00 PM', '01:30 AM', '2026-04-15', '5h 30m', 'Semi-Sleeper', 380),

-- Chennai to Vellore
('TNSTC Volvo', 'TN01-1212', 'Chennai', 'Vellore', '06:00 AM', '09:00 AM', '2026-04-15', '3h 00m', 'AC', 180),
('Parveen Travels', 'TN22-1313', 'Chennai', 'Vellore', '07:00 AM', '10:00 AM', '2026-04-15', '3h 00m', 'Non-AC', 120),
('SRS Travels', 'TN33-1414', 'Chennai', 'Vellore', '09:00 AM', '12:00 PM', '2026-04-15', '3h 00m', 'Non-AC', 130),

-- Chennai to Goa
('VRL Travels', 'KA01-1111', 'Chennai', 'Goa', '04:00 PM', '10:00 AM', '2026-04-15', '18h 00m', 'AC Sleeper', 1100),
('SRM Travels', 'TN07-2222', 'Chennai', 'Goa', '05:00 PM', '11:00 AM', '2026-04-15', '18h 00m', 'Sleeper', 950),
('Orange Travels', 'TN45-3333', 'Chennai', 'Goa', '06:00 PM', '12:00 PM', '2026-04-16', '18h 00m', 'Semi-Sleeper', 850),

-- Bangalore to Chennai
('VRL Travels', 'KA01-1515', 'Bangalore', 'Chennai', '09:00 PM', '03:30 AM', '2026-04-15', '6h 30m', 'AC Sleeper', 700),
('KPN Travels', 'TN09-1616', 'Bangalore', 'Chennai', '10:00 PM', '04:30 AM', '2026-04-15', '6h 30m', 'AC Sleeper', 650),
('SRM Travels', 'TN07-1717', 'Bangalore', 'Chennai', '08:30 PM', '03:00 AM', '2026-04-15', '6h 30m', 'Sleeper', 550),
('Orange Travels', 'TN45-1818', 'Bangalore', 'Chennai', '11:00 PM', '05:30 AM', '2026-04-15', '6h 30m', 'Semi-Sleeper', 480),

-- Hyderabad to Chennai
('VRL Travels', 'KA01-1919', 'Hyderabad', 'Chennai', '05:00 PM', '07:00 AM', '2026-04-15', '14h 00m', 'AC Sleeper', 950),
('SRM Travels', 'TN07-2020', 'Hyderabad', 'Chennai', '06:00 PM', '08:00 AM', '2026-04-15', '14h 00m', 'Sleeper', 800),

-- Madurai to Chennai
('KPN Travels', 'TN09-2121', 'Madurai', 'Chennai', '08:00 PM', '03:30 AM', '2026-04-15', '7h 30m', 'AC Sleeper', 580),
('TNSTC Volvo', 'TN01-2222', 'Madurai', 'Chennai', '09:00 PM', '04:30 AM', '2026-04-15', '7h 30m', 'AC', 480),
('Parveen Travels', 'TN22-2323', 'Madurai', 'Chennai', '07:00 PM', '02:30 AM', '2026-04-15', '7h 30m', 'Non-AC', 280),

-- Coimbatore to Chennai
('KPN Travels', 'TN09-2424', 'Coimbatore', 'Chennai', '09:30 PM', '05:30 AM', '2026-04-15', '8h 00m', 'AC Sleeper', 620),
('TNSTC Volvo', 'TN01-2525', 'Coimbatore', 'Chennai', '10:00 PM', '06:00 AM', '2026-04-15', '8h 00m', 'AC', 500),
('SRS Travels', 'TN33-2626', 'Coimbatore', 'Chennai', '08:00 PM', '04:00 AM', '2026-04-15', '8h 00m', 'Non-AC', 300),

-- Bangalore to Hyderabad
('VRL Travels', 'KA01-2929', 'Bangalore', 'Hyderabad', '06:00 PM', '01:00 AM', '2026-04-15', '7h 00m', 'AC Sleeper', 750),
('SRM Travels', 'TN07-3030', 'Bangalore', 'Hyderabad', '07:00 PM', '02:00 AM', '2026-04-15', '7h 00m', 'Sleeper', 650),
('Orange Travels', 'TN45-3131', 'Bangalore', 'Hyderabad', '08:00 PM', '03:00 AM', '2026-04-15', '7h 00m', 'Semi-Sleeper', 580),

-- Hyderabad to Bangalore
('VRL Travels', 'KA01-3232', 'Hyderabad', 'Bangalore', '07:00 PM', '02:00 AM', '2026-04-15', '7h 00m', 'AC Sleeper', 780),
('KPN Travels', 'TN09-3333', 'Hyderabad', 'Bangalore', '08:00 PM', '03:00 AM', '2026-04-15', '7h 00m', 'AC Sleeper', 720),

-- Mumbai to Goa
('VRL Travels', 'MH01-3434', 'Mumbai', 'Goa', '06:00 PM', '02:00 AM', '2026-04-15', '8h 00m', 'AC Sleeper', 850),
('SRS Travels', 'MH02-3535', 'Mumbai', 'Goa', '07:00 PM', '03:00 AM', '2026-04-15', '8h 00m', 'Semi-Sleeper', 650),
('Orange Travels', 'MH03-3636', 'Mumbai', 'Goa', '08:00 PM', '04:00 AM', '2026-04-15', '8h 00m', 'Sleeper', 700),

-- Delhi to Agra
('TNSTC Volvo', 'DL01-3737', 'Delhi', 'Agra', '06:00 AM', '10:30 AM', '2026-04-15', '4h 30m', 'AC', 400),
('KPN Travels', 'DL02-3838', 'Delhi', 'Agra', '08:00 AM', '12:30 PM', '2026-04-15', '4h 30m', 'Non-AC', 250),
('Parveen Travels', 'DL03-3939', 'Delhi', 'Agra', '10:00 AM', '02:30 PM', '2026-04-15', '4h 30m', 'Non-AC', 230);

INSERT INTO movie (title, language, genre, release_date, status, rating, image, duration) VALUES
('How to Train Your Dragon', 'English', 'Adventure', '2026-06-01', 'Now Showing', 4.8, 'movie-1.jpg', '1h 42m'),
('DNA', 'Tamil', 'Thriller', '2026-05-15', 'Now Showing', 4.5, 'movie-2.jpg', '2h 10m'),
('Elio', 'English', 'Comedy', '2026-06-13', 'Now Showing', 4.3, 'movie-3.jpg', '1h 38m'),
('Maargan', 'Tamil', 'Action', '2026-05-01', 'Now Showing', 4.6, 'movie-4.jpg', '2h 25m'),
('BabaYaga', 'English', 'Horror', '2025-12-25', 'Coming Soon', 0.0, 'movie-5.jpg', '2h 05m'),
('Lilo & Stitch', 'English', 'Adventure', '2026-05-23', 'Now Showing', 4.7, 'movie-6.jpg', '1h 48m'),
('Mission Impossible', 'English', 'Action', '2026-05-20', 'Now Showing', 4.9, 'movie-7.jpg', '2h 45m'),
('Pushpa 3', 'Telugu', 'Action', '2026-12-25', 'Coming Soon', 0.0, 'movie-8.jpg', '3h 00m'),
('Kantara 2', 'Kannada', 'Drama', '2026-10-01', 'Coming Soon', 0.0, 'movie-9.jpg', '2h 30m'),
('Avengers Doomsday', 'English', 'Action', '2026-05-01', 'Now Showing', 4.8, 'movie-10.jpg', '2h 58m'),
('RRR 2', 'Telugu', 'Drama', '2026-08-15', 'Coming Soon', 0.0, 'movie-11.jpg', '3h 10m'),
('KGF 3', 'Kannada', 'Action', '2026-09-20', 'Coming Soon', 0.0, 'movie-12.jpg', '2h 50m');

DELETE FROM event;
ALTER TABLE event AUTO_INCREMENT = 1;

INSERT INTO event (name, category, venue, city, event_date, event_time, price, rating, image, description) VALUES
('Sunburn Music Festival', 'Music', 'YMCA Ground', 'Chennai', '12 Oct 2025', '6:00 PM', 250, 4.9, 'event-1.jpg', 'Indias biggest electronic music festival'),
('Stand-Up Comedy Night', 'Comedy', 'Chinnaswamy Stadium', 'Bangalore', '25 Sep 2025', '8:30 PM', 250, 4.7, 'event-2.jpg', 'Top comedians performing live'),
('Food Carnival 2025', 'Food Festival', 'Hitex Exhibition', 'Hyderabad', '15 Nov 2025', '4:00 PM', 250, 4.5, 'event-3.jpg', 'Best food from across India'),
('AI & ML Workshop', 'Workshop', 'IIT Madras', 'Chennai', '20 Oct 2025', '9:00 AM', 500, 4.8, 'event-4.jpg', 'Hands on AI workshop for developers'),
('Street Food Festival', 'Food Festival', 'Marina Beach', 'Chennai', '10 Nov 2025', '12:00 PM', 150, 4.6, 'event-5.jpg', 'Street food from every corner of India'),
('React India Workshop', 'Workshop', 'IIT Bombay', 'Mumbai', '05 Dec 2025', '10:00 AM', 800, 4.7, 'event-6.jpg', 'Frontend development workshop'),
('Cultural Fest 2025', 'Cultural', 'Palace Grounds', 'Bangalore', '25 Dec 2025', '5:00 PM', 300, 4.8, 'event-7.jpg', 'Celebrate culture with music and dance'),
('Jazz Music Night', 'Music', 'Phoenix Mall', 'Chennai', '18 Oct 2025', '7:00 PM', 600, 4.6, 'event-8.jpg', 'Evening of soulful jazz music'),
('Food Fest Bangalore', 'Food Festival', 'UB City', 'Bangalore', '22 Nov 2025', '11:00 AM', 200, 4.4, 'event-9.jpg', 'Gourmet food from top chefs'),
('Comedy Nights Hyderabad', 'Comedy', 'Shilpa Kala Vedika', 'Hyderabad', '30 Oct 2025', '7:30 PM', 400, 4.5, 'event-10.jpg', 'Laugh out loud comedy show');


DELETE FROM hotel;
ALTER TABLE hotel AUTO_INCREMENT = 1;

INSERT INTO hotel (name, category, city, address, price_per_night, rating, amenities, image, description) VALUES

-- Popular Destination Cards (id 1-6) — images p-1 to p-6
('Ocean Pearl Resort', 'Beach Vacations', 'Goa', 'Calangute Beach, Goa', 4500, 4.8, 'WiFi | Pool | Spa | Restaurant', 'p-1.jpg', 'Luxury beach resort with ocean views'),
('Blue Wave Beach Hotel', 'Beach Vacations', 'Goa', 'Baga Beach, Goa', 3500, 4.6, 'WiFi | Pool | Bar | Restaurant', 'p-2.jpg', 'Beachfront hotel with stunning sunsets'),
('Misty Peak Lodge', 'Mountain Calling', 'Shimla', 'Mall Road, Shimla', 3500, 4.7, 'WiFi | Heater | Fireplace | Spa', 'p-3.jpg', 'Stunning mountain views'),
('Royal Heritage Palace', 'Stay Like Royals', 'Jaipur', 'City Palace Area, Jaipur', 8000, 4.9, 'WiFi | Pool | Spa | Royal Dining', 'p-4.jpg', 'Experience royal Rajasthan hospitality'),
('Green Valley Retreat', 'Weekend Gateways', 'Coorg', 'Madikeri, Coorg', 2500, 4.5, 'WiFi | Garden | Trekking | Bonfire', 'p-5.jpg', 'Perfect weekend escape in the hills'),
('Neon Nights Hotel', 'Party Destinations', 'Mumbai', 'Juhu Beach, Mumbai', 5500, 4.6, 'WiFi | Pool | Club | Rooftop Bar', 'p-6.jpg', 'Party central with rooftop club'),

-- Beach Vacations (id 7-10) — tab cards
('Coral Bay Retreat', 'Beach Vacations', 'Pondicherry', 'Promenade Beach, Pondicherry', 3000, 4.5, 'WiFi | Pool | Yoga | Beach', 'hotel-1.jpg', 'Peaceful beach retreat'),
('Sunset Shore Resort', 'Beach Vacations', 'Kerala', 'Kovalam Beach, Kerala', 5000, 4.9, 'WiFi | Pool | Ayurveda | Spa', 'hotel-2.jpg', 'Premium ayurvedic beach resort'),
('Pearl Bay Hotel', 'Beach Vacations', 'Goa', 'Anjuna Beach, Goa', 4000, 4.7, 'WiFi | Pool | Beach Bar | Spa', 'hotel-3.jpg', 'Beautiful beach hotel in Anjuna'),
('Marina Bliss Resort', 'Beach Vacations', 'Chennai', 'ECR Road, Chennai', 2800, 4.3, 'WiFi | Pool | Restaurant | Gym', 'hotel-4.jpg', 'Resort on the East Coast Road'),

-- Weekend Gateways (id 11-14)
('Serene Escape Resort', 'Weekend Gateways', 'Ooty', 'Ooty Lake Road, Ooty', 2800, 4.4, 'WiFi | Pool | Garden | Spa', 'hotel-5.jpg', 'Serene resort for weekend getaways'),
('Palm Grove Stay', 'Weekend Gateways', 'Munnar', 'Tea Valley Road, Munnar', 3200, 4.6, 'WiFi | Garden | Trekking | Bonfire', 'hotel-6.jpg', 'Cozy stay amidst tea estates'),
('Tranquil Woods Resort', 'Weekend Gateways', 'Wayanad', 'Forest Road, Wayanad', 2200, 4.3, 'WiFi | Pool | Nature Walks | Bonfire', 'hotel-7.jpg', 'Peaceful resort in the woods'),
('Riverside Cottage', 'Weekend Gateways', 'Kodaikanal', 'Lake Road, Kodaikanal', 2600, 4.5, 'WiFi | Garden | Trekking | Bonfire', 'hotel-8.jpg', 'Cozy riverside cottage'),

-- Mountain Calling (id 15-18)
('Alpine View Resort', 'Mountain Calling', 'Manali', 'Solang Valley, Manali', 4000, 4.8, 'WiFi | Heater | Adventure Sports | Spa', 'hotel-9.jpg', 'Luxury resort in the Himalayas'),
('Snow Ridge Retreat', 'Mountain Calling', 'Mussoorie', 'Mall Road, Mussoorie', 3800, 4.6, 'WiFi | Heater | Fireplace | Trekking', 'hotel-10.jpg', 'Snowy mountain retreat'),
('Cloud Valley Resort', 'Mountain Calling', 'Darjeeling', 'Tea Garden Road, Darjeeling', 3000, 4.5, 'WiFi | Heater | Garden | Tea Tours', 'hotel-11.jpg', 'Resort in the clouds'),
('Himalayan Heights Hotel', 'Mountain Calling', 'Leh', 'Leh Market Road, Leh', 4500, 4.7, 'WiFi | Heater | Adventure | Trekking', 'hotel-12.jpg', 'High altitude luxury hotel'),

-- Stay Like Royals (id 19-22)
('Imperial Crown Hotel', 'Stay Like Royals', 'Udaipur', 'Lake Pichola View, Udaipur', 7000, 4.8, 'WiFi | Pool | Lake View | Spa', 'hotel-13.jpg', 'Stunning lake view heritage hotel'),
('Grand Maharaja Palace', 'Stay Like Royals', 'Mysore', 'Palace Road, Mysore', 9000, 4.9, 'WiFi | Pool | Royal Dining | Spa', 'hotel-14.jpg', 'Stay like a Maharaja in Mysore'),
('Regal Dynasty Resort', 'Stay Like Royals', 'Jodhpur', 'Mehrangarh Fort Area, Jodhpur', 7500, 4.7, 'WiFi | Pool | Desert Safari | Spa', 'hotel-15.jpg', 'Royal stay in the blue city'),
('Nawab Palace Hotel', 'Stay Like Royals', 'Hyderabad', 'Banjara Hills, Hyderabad', 6500, 4.6, 'WiFi | Pool | Royal Dining | Spa', 'hotel-16.jpg', 'Nawabi hospitality in Hyderabad'),

-- Party Destinations (id 23-26)
('Skyline Party Suites', 'Party Destinations', 'Bangalore', 'MG Road, Bangalore', 4500, 4.5, 'WiFi | Pool | Club | Bar | Rooftop', 'hotel-17.jpg', 'Best nightlife in Bangalore'),
('Club Vista Resort', 'Party Destinations', 'Goa', 'Anjuna Beach, Goa', 6000, 4.7, 'WiFi | Pool | Club | Beach Bar | DJ', 'hotel-18.jpg', 'Ultimate party resort in Goa'),
('Vibe City Hotel', 'Party Destinations', 'Delhi', 'Connaught Place, Delhi', 5000, 4.4, 'WiFi | Pool | Club | Rooftop Bar', 'hotel-19.jpg', 'Vibrant city hotel with rooftop'),
('Pulse Nightlife Hotel', 'Party Destinations', 'Chennai', 'Anna Salai, Chennai', 4000, 4.3, 'WiFi | Pool | Club | Bar', 'hotel-20.jpg', 'Best party hotel in Chennai');

ALTER TABLE movie ADD COLUMN price INT DEFAULT 200;

UPDATE movie SET price = 200 WHERE status = 'Now Showing';
UPDATE movie SET price = 0 WHERE status = 'Coming Soon';

SELECT id, flight_id, selected_seats, booking_status 
FROM flight_booking 
WHERE flight_id = 4;

ALTER TABLE bus_booking DROP COLUMN seat_numbers;

show tables;

select * from users;

select * from flight;
select * from flight_booking;

select * from train;
select * from train_booking;

select * from bus;
select * from bus_booking;

select * from movie;
select * from movie_booking;

select * from event;
select * from event_booking;

select * from sport;
select * from sport_booking;

select * from activity;

select * from hotel;
select * from hotel_booking;

select * from booking;


-- truncate users;

-- truncate flight;
truncate flight_booking;

-- truncate train ;
truncate train_booking ;

truncate bus_booking;
