package com.example.cinepick_be.dto;

import com.example.cinepick_be.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
   private Long commentId;
   private String content;
   private String userId;
   private Long movieId;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;


   public CommentDTO(Long id, String content, String userId, Long movie, LocalDateTime updatedAt) {
      this.commentId=id;
      this.content=content;
      this.userId=userId;
      this.movieId=movieId;
      this.updatedAt=updatedAt;
   }
}
