package com.example.cinepick_be.controller;

import com.example.cinepick_be.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("/tmdb")
public class TmdbController {

   private final TmdbService tmdbService;

   @GetMapping("/movies/all")
   public ResponseEntity<String> getMovies(){
      tmdbService.getMovies();
      return ResponseEntity.ok("tmdb popular 출력.");
   }

   @GetMapping("/movies/search")
   public ResponseEntity<String> searchMovie(@RequestParam String keyword){
      tmdbService.searchMovie(keyword);
      return ResponseEntity.ok(keyword +" 검색");

   }

   @GetMapping("/movies/filter")
   public ResponseEntity<String> filterMovie(@RequestParam List<Integer> genres){
      tmdbService.filterMovie(genres);
      return ResponseEntity.ok("장르 필터링 완료");
   }
}
