package com.example.cinepick_be.dto;

import com.example.cinepick_be.entity.Like;
import com.example.cinepick_be.entity.Movie;
import com.example.cinepick_be.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
   private Long id;
   private String user;
   private MovieDTO movieDTO;

   //엔티티를 eto로 변환
   public LikeDTO(String user, Movie movie) {
      this.user= user;
      String imageUrl= movie.getImageUrl();
      if (imageUrl != null && !imageUrl.isEmpty()) {
         imageUrl = imageUrl.toLowerCase();
         if (imageUrl.endsWith(".jpg")) {
            imageUrl = "http://3.105.163.214:8080/uploads/movies/" + imageUrl;
         }
      }
      this.movieDTO= new MovieDTO(movie.getTitle(),imageUrl);
   }
}
