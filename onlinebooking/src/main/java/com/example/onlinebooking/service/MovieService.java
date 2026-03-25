package com.example.onlinebooking.service;

import com.example.onlinebooking.model.Movie;
import com.example.onlinebooking.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found: " + id));
    }

   public List<Movie> filterMovies(List<String> genre, String language, String status, String title) {
    return movieRepository.findAll().stream()
            .filter(m -> genre == null || genre.isEmpty() || genre.contains(m.getGenre()))
            .filter(m -> language == null || language.equals("all") || language.equals(m.getLanguage()))
            .filter(m -> status == null || status.equals("all") || status.equals(m.getStatus()))
            .filter(m -> title == null || title.isEmpty() || m.getTitle().toLowerCase().contains(title.toLowerCase()))
            .toList();
}
}