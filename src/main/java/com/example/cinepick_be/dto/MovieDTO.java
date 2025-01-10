package com.example.cinepick_be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
   private String title; // 영화 제목
   @Lob
   private String overview; // 줄거리
   @JsonProperty("poster_path")
   private String posterPath; // 이미지 URL
   @JsonProperty("genre_ids")
   private List<Integer> genreIds; // 장르 ID 목록

   private List<String> genres;


   public MovieDTO(String title, String imageUrl, String plot, String s) {
   }
}
