package com.example.cinepick_be.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TmdbService {

   @Value("${tmdb.token}")
   private String tmdbToken;

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
      String url = "https://api.themoviedb.org/3/movie/popular?language=ko-KR&page=1";
      ResponseEntity<String> response =getHeader(url);

      System.out.println(response);
      return response;
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
