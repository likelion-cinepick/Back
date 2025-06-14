package com.example.cinepick_be.service;

import com.example.cinepick_be.entity.User;
import com.example.cinepick_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TmdbService {

   private final UserRepository userRepository;
   @Value("${tmdb.token}")
   private String tmdbToken;

   public TmdbService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   // TMDB 헤더
   public ResponseEntity<String> getHeader(String url){
      HttpHeaders headers= new HttpHeaders();
      headers.set("Authorization","Bearer "+tmdbToken);

      HttpEntity<String> entity = new HttpEntity<>(headers);

      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
      return response;
   }

   // TMDB 영화 전체 조회
   public ResponseEntity<String> getMovies() {

      String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/trending/movie/week")
            .queryParam("language","ko-KR")
            .toUriString();
      ResponseEntity<String> response =getHeader(url);

      System.out.println(response);
      return response;
   }

   // TMDB 영화 성향 별 조회
   public ResponseEntity<String> getRecommendMovies(String username){

      User user = userRepository.findByUserId(username)
            .orElseThrow(() -> new AccessDeniedException(""));
      List<Integer> userGenres ;
                  String uri = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/discover/movie")
                        .queryParam("with_genres",genre)
                        .queryParam("language","ko-KR")
   }


   // TMDB 영화 찾기
   public ResponseEntity<String> searchMovie(String keyword){
      String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/search/movie")
            .queryParam("query", keyword)
            .queryParam("language","ko-KR")
            .toUriString();

      ResponseEntity<String> response =getHeader(url);

      return response;

   }

   // TMDB 영화 필터링
   public ResponseEntity<String> filterMovie(List<Integer> genres){
      String genre = genres.stream().map(String::valueOf).collect(Collectors.joining(","));
      String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/discover/movie")
            .queryParam("with_genres",genre)
            .queryParam("language","ko-KR")
            .toUriString();

      ResponseEntity<String> response =getHeader(url);

      return response;
   }

}
