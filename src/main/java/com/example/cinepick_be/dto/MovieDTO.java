package com.example.cinepick_be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
   private String title; // 제목
   @Lob
   private String overview; // 줄거리
   @JsonProperty("poster_path")
   private String posterPath; // 이미지 URL
   @JsonProperty("genre_ids")
   private List<Integer> genreIds; // 장르 ID 목록

   private List<String> genres;

   private String genre;


   public MovieDTO(String title, String imageUrl) {
      this.title = title;
      this.posterPath = imageUrl;
   }

   public MovieDTO(String title, String imageUrl, String plot, String genre) {
      this.title = title;
      this.posterPath = imageUrl;
      this.overview=plot;
      this.genre= genre;
   }
}
