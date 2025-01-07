package com.example.cinepick_be.dto;

import com.example.cinepick_be.entity.Like;
import com.example.cinepick_be.entity.Movie;
import com.example.cinepick_be.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
   private Long id;
   private String user;
   private Long movie;

   //엔티티를 eto로 변환
   public LikeDTO(String user, Long movie) {
      this.user= user;
      this.movie= movie;
   }
}
