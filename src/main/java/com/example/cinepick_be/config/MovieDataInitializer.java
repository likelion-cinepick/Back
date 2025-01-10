package com.example.cinepick_be.config;

import com.example.cinepick_be.entity.Movie;
import com.example.cinepick_be.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MovieDataInitializer implements CommandLineRunner {

   private final MovieService movieService;

   public MovieDataInitializer(MovieService movieService) {
      this.movieService = movieService;
   }

   @Override
   public void run(String... args) throws Exception {
      System.out.println("Initializing movie database...");
      try {
//         movieService.fetchAndSaveMovies();
         System.out.println("Movie database initialized successfully!");
      } catch (Exception e) {
         System.err.println("Error occurred while initializing movie database: " + e.getMessage());
      }
   }
}

