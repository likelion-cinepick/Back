package com.example.cinepick_be.controller;

import com.example.cinepick_be.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

   // 메인 페이지
   @GetMapping("/movies/all")
   public ResponseEntity<String> getMovies(){
      ResponseEntity<String> response = tmdbService.getMovies();
      return response;
   }

   // 메인 페이지(성격 별 추천 영화)
   @GetMapping("/movies/personal")
   public ResponseEntity<String> getRecommendMovies(@AuthenticationPrincipal UserDetails userDetails){
      ResponseEntity<String> response = tmdbService.getRecommendMovies(userDetails.getUsername());
      return response;
   }

   // 영화 검색
   @GetMapping("/movies/search")
   public ResponseEntity<String> searchMovies(@RequestParam String keyword){
      ResponseEntity<String> response = tmdbService.searchMovie(keyword);
      return response;
   }

   // 영화 필터링 검색
   @GetMapping("/movies/filter")
   public ResponseEntity<String> filterMovies(@RequestParam List<Integer> genres){
      ResponseEntity<String> response = tmdbService.filterMovie(genres);
      return response;
   }


}
