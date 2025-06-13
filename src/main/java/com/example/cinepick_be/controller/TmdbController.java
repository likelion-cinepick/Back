package com.example.cinepick_be.controller;

import com.example.cinepick_be.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tmdb")
public class TmdbController {

   private final TmdbService tmdbService;

   @GetMapping("/movies/all")
   public ResponseEntity<String> getMovies(){
      ResponseEntity<String> response = tmdbService.getMovies();
      return response;
   }

   @GetMapping("/movies/search")
   public ResponseEntity<String> searchMovie(@RequestParam String keyword){
      ResponseEntity<String> response = tmdbService.searchMovie(keyword);
      return response;
   }

   @GetMapping("/movies/filter")
   public ResponseEntity<String> filterMovie(@RequestParam List<Integer> genres){
      ResponseEntity<String> response = tmdbService.filterMovie(genres);
      return response;
   }
}
