package com.example.cinepick_be.dto;

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
}
