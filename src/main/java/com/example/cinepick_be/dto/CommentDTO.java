package com.example.cinepick_be.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
   private Long id;
   private String content;
   private String userId;
   private Long movieId;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
}
