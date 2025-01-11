package com.example.cinepick_be.dto;

import com.example.cinepick_be.entity.Recommend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendDTO {
   private Long recommendId;
   private String content;
   private String userId;
   private Long movieId;
   private LocalDateTime createdAt;

   //엔티티를 dto로 변환
   public RecommendDTO(Recommend recommend) {
      this.recommendId = recommend.getId();
      this.content = recommend.getContent();
      this.userId = recommend.getUser().getUserId();  // User 객체에서 userId 추출
      this.movieId = recommend.getMovie().getId();  // Movie 객체에서 movieId 추출
      this.createdAt = recommend.getCreatedAt();
   }

}
